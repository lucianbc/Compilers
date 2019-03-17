cmake_minimum_required(VERSION 3.13)
project(LexicalAnalysis)

set(CMAKE_CXX_STANDARD 17)

include_directories(inc)
include_directories(src)
include_directories(lib)

file(GLOB LexicalAnalysisLib
        "./lib/*.cpp"
        "./lib/*.cc")

file(GLOB LexicalAnalysisHeaders
        "./inc/*.h")

find_package(FLEX REQUIRED)
FLEX_TARGET(Scanner lib/grammarC.l ${CMAKE_CURRENT_BINARY_DIR}/lexer.cpp)

file(COPY ./test DESTINATION ./)

add_executable(LexicalAnalysis ./src/main.cpp ${LexicalAnalysisLib} ${LexicalAnalysisHeaders} ${FLEX_Scanner_OUTPUTS})
add_executable(Other ./src/other.cpp ${LexicalAnalysisLib} ${LexicalAnalysisHeaders} ${FLEX_Scanner_OUTPUTS})
target_link_libraries(LexicalAnalysis ${FLEX_LIBRARIES})
