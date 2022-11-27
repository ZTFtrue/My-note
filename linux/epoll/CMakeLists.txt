cmake_minimum_required (VERSION 2.6)

project (epoll)

FIND_PACKAGE(PkgConfig REQUIRED)

set(CMAKE_CXX_STANDARD 17)

include_directories(src)
file(GLOB SOURCES "src/*.h" "src/*.cpp" )
add_executable(epoll ${SOURCES} src/epoll.cpp)