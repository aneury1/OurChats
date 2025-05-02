cmake_minimum_required(VERSION 3.14)
project(WebSocketServer)

set(CMAKE_CXX_STANDARD 11)

include(FetchContent)
FetchContent_Declare(
        websocketpp
        GIT_REPOSITORY https://github.com/zaphoyd/websocketpp.git
        GIT_TAG        master
)
FetchContent_MakeAvailable(websocketpp)


FetchContent_Declare(
        jsoncpp
        GIT_REPOSITORY https://github.com/open-source-parsers/jsoncpp.git
        GIT_TAG        1.9.5
)

FetchContent_MakeAvailable(jsoncpp)

find_package(Boost REQUIRED system thread)

add_executable(WebSocketServer websocket.cpp)

target_include_directories(WebSocketServer PRIVATE
        ${websocketpp_SOURCE_DIR}
        ${jsoncpp_SOURCE_DIR}
        ${Boost_INCLUDE_DIRS}
)

target_link_libraries(WebSocketServer
        ${Boost_LIBRARIES}
        pthread
        jsoncpp_lib
)
