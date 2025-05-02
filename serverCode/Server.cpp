#include <websocketpp/config/asio_no_tls.hpp>
#include <websocketpp/server.hpp>

#include <iostream>
#include <set>
#include <map>
#include <vector>

typedef websocketpp::server<websocketpp::config::asio> server;
using websocketpp::connection_hdl;
using message_ptr = server::message_ptr;

static int counter = 0;

enum class TypeOfRequest
{
    ChatAll,
    RegisterNick,
    History
};

struct User{
    std::string user;
    std::string about;
    std::string email_phone_or_id;
};

struct Message{
   std::string user;
   std::string timestamp;
   std::string message;
};

std::string TypeOfRequestAsString(const TypeOfRequest &type)
{
    switch (type)
    {
        case TypeOfRequest::RegisterNick:
            return "RegisterNick";
            break;
        case TypeOfRequest::History:
            return "History";
            break;
        case TypeOfRequest::ChatAll:
            return "Chat All";
            break;
    }
    return "NOT IN THE List";
}

class WebSocketServer
{
public:
    std::vector<std::string> allmsg;
    std::map<std::string, std::string> userNames;

    std::string get_connection_id(connection_hdl hdl)
    {
        // This method returns the unique ID of the connection as a string
        return std::to_string(reinterpret_cast<uintptr_t>(&hdl));
    }

    TypeOfRequest getTypeOfMsg(std::string payload)
    {
        if (payload.find("user-register:") != std::string::npos)
            return TypeOfRequest::RegisterNick;
        if (payload.find("history:") != std::string::npos)
            return TypeOfRequest::History;
        return TypeOfRequest::ChatAll;
    }

    void sendToOne(connection_hdl hdl, message_ptr msg, std::string userName)
    {
        websocketpp::lib::error_code ec;
        m_server.send(hdl, userName + ":" + msg->get_payload(), websocketpp::frame::opcode::text, ec);
    }

    void sendBroadcastExcept(connection_hdl chdl, message_ptr msg, std::string userName)
    {
        ///       std::lock_guard<std::mutex> lock(m_mutex);
        for (const auto &hdl : m_connections)
        {
            websocketpp::lib::error_code ec;
            // if(chdl==hdl.lock())
            //   continue;

            m_server.send(hdl, userName + ":" + msg->get_payload(), websocketpp::frame::opcode::text, ec);
            if (ec)
            {
                std::cout << "Send failed: " << ec.message() << std::endl;
            }
        }
    }


    void registerNickname(connection_hdl hdl,const std::string& pmsg){
        std::string id= get_connection_id(hdl);
        int pos = pmsg.find("user-register:");
        if(pos==std::string::npos)return;
        std::string s = pmsg.substr(pos, pmsg.size()-(pos+14));
        userNames[id]=s;
        std::cout <<"USUARIO: "<<id<<" Registrado con el Nick:"<<s<<"\n";
    }

    std::string getUser(connection_hdl id){

        auto st = get_connection_id(id);
        auto name = userNames[st];
        if(name.empty()){
            return "ANOM";
        }
        return name;
        //// return userNames[st].empty()?"ANOM":userNames[st];
    }



    void handlePayload(connection_hdl hdl, message_ptr msg)
    {

        auto pinger = get_connection_id(hdl);

        std::string payload = msg->get_payload();

        std::cout << "Customer: " << pinger << "\n"
                  << payload << "\n";

        auto type = getTypeOfMsg(payload);
        std::cout << "MessageType@" << TypeOfRequestAsString(type) << "\n";
        switch (type)
        {
            case TypeOfRequest::RegisterNick:
                registerNickname(hdl, payload);
                break;
            case TypeOfRequest::History:

            case TypeOfRequest::ChatAll:
                sendBroadcastExcept(hdl, msg, "<"+getUser(hdl)+">");
        }
    }

    WebSocketServer()
    {
        m_server.init_asio();

        m_server.set_open_handler([this](connection_hdl hdl)
                                  { m_connections.insert(hdl); });

        m_server.set_close_handler([this](connection_hdl hdl)
                                   { m_connections.erase(hdl); });

        m_server.set_message_handler([this](connection_hdl hdl, message_ptr msg)
                                     {
                                         handlePayload(hdl, msg);
                                     });
    }

    void run(uint16_t port)
    {
        m_server.listen(port);
        m_server.start_accept();
        m_server.run();
    }

private:
    server m_server;
    std::set<connection_hdl, std::owner_less<connection_hdl>> m_connections;
};

int main()
{
    WebSocketServer server;
    server.run(9002); // Run server on port 9002
    return 0;
}
