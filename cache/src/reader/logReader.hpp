#ifndef LOGREADER_HPP
#define LOGREADER_HPP

#include <string>
#include "../cache/cache.hpp"

class LogReader
{
public:
    LogReader(int clientLimit);
    Cache<std::string> loadCache();

private:
    std::string logName = "logfile.txt";
    int memoryLimit;
};

#endif