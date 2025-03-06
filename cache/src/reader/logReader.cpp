#include "logReader.hpp"
#include <iostream>
#include <fstream>
#include <regex>

LogReader::LogReader(int clientLimit) : memoryLimit(clientLimit) {}

Cache<std::string> LogReader::loadCache()
{
    std::ifstream LogFile(logName);
    std::string line;
    std::string delimiter = ";";
    Cache<std::string> cache(memoryLimit);

    while (getline(LogFile, line))
    {
        std::string key = line.substr(0, line.find(delimiter));
        std::string value = line.substr(line.find(delimiter), delimiter.length());
        cache.add(key, value);
    }
    LogFile.close();
    return cache;
}