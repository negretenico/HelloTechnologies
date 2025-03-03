#include <unordered_map>
using namespace std;
template <typename T>
class Cache
{
private:
    unordered_map<string, T> cache;
    size_t memoryUsed = 0;
    size_t MEMORY_LIMIT;

public:
    Cache(size_t client_limit) : MEMORY_LIMIT(client_limit) {}

    void add(const string &key, const T &item)
    {
        size_t itemSize = sizeof(item) + key.size();
        if (memoryUsed + itemSize > MEMORY_LIMIT)
        {
            // Evict using some strategy
            evict([](const unordered_map<string, T> &cache)
                  {
                      // Example placeholder eviction policy (LRU or Random)
                      if (cache.empty()){
                        return string(); // empty string if no eviction possible
                      }

                    return cache.begin()->first; });
        }
        cache[key] = item;
        memoryUsed += itemSize;
    }

    T get(const string &key)
    {
        return cache.at(key)
    }
    void evict(function<string(const unordered_map<string, T> &)> strategy)
    {
        string keyToEvict = strategy(cache);
        if (keyToEvict.empty())
        {
            return;
        }
        memoryUsed -= sizeof(cache[keyToEvict]) + keyToEvict.size();
        cache.erase(keyToEvict);
    }
};