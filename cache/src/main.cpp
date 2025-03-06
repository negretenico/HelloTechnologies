#include <iostream>
#include <unordered_map>
#include "cache/cache.hpp"
#include "reader/logReader.hpp"
#include "writer/logWriter.hpp"
using namespace std;
int main(int argc, char *argv[])
{
    if (argc < 2)
    {
        cerr << "Usage: ./program <command> [args...]" << endl;
        return 1;
    }

    LogReader logreader(100);
    LogWriter logwriter;
    Cache<std::string> cache = logreader.loadCache();

    string command = argv[1];

    if (command == "add")
    {
        if (argc < 4)
        {
            cerr << "Usage for add: ./program add <key> <value>" << endl;
            return 1;
        }
        string key = argv[2];
        string value = argv[3];
        cache.add(key, value);
        logwriter.write(key, value);
    }
    else if (command == "get")
    {
        if (argc < 3)
        {
            cerr << "Usage for get: ./program get <key>" << endl;
            return 1;
        }
        string key = argv[2];
        cout << "Value: " << cache.get(key) << endl;
    }
    else if (command == "evict")
    {
        // Example eviction strategy that evicts the first item (placeholder)
        cache.evict([](const unordered_map<string, string> &map) -> string
                    { return map.begin()->first; });
    }
    else
    {
        cerr << "Unknown command: " << command << endl;
        return 1;
    }

    return 0;
}
