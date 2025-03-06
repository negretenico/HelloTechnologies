#ifndef CACHE_HPP
#define CACHE_HPP
#include <string>
#include <functional>

template <typename T>
class Cache
{
public:
    Cache(int memSize) {} // Constructor implementation for the example
    void add(const std::string &key, const T &item) {}
    T get(const std::string &key) { return T(); }
    void evict(std::function<std::string(const std::unordered_map<std::string, T> &)> strategy) {}
};
#endif