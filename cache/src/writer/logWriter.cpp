#include "logWriter.hpp"
#include <iostream>
#include <fstream>

Result LogWriter::write(std::string &key, std::string &value)
{
    std::ofstream LogFile(logName, std::ios::app); // Open in append mode
    if (!LogFile.is_open())
    {
        return FAILURE; // Handle case where file couldn't be opened
    }

    LogFile << key << ";" << value << std::endl; // Write directly
    if (LogFile.fail())
    {
        return FAILURE; // Something went wrong during the write
    }

    return SUCCESS;
}