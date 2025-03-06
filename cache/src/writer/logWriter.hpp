#ifndef LOGWRITER_HPP
#define LOGWRITER_HPP

#include <string>
#include "../result.hpp"

class LogWriter
{
private:
    std::string logName = "logfile.txt";

public:
    LogWriter() {};
    Result write(std::string &key, std::string &value);
};
#endif