#include <unordered_map>
#include <string>
#include <functional>
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
        return cache.at(key);
    }
    void evict(function<string(const unordered_map<string, T> &)> strategy)
    {
        string keyToEvict = strategy(cache); // Get key to evict using the strategy
        if (keyToEvict.empty())              // If no key is returned, don't do anything
        {
            return;
        }

        // Adjust memoryUsed: subtract the size of the string and the key
        memoryUsed -= cache[keyToEvict].size() + keyToEvict.size(); // For strings, use size()

        cache.erase(keyToEvict); // Erase the entry from the cache
    }
};