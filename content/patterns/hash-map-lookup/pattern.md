---
id: "hash-map-lookup"
name: "Hash Map Lookup"
category: "HASHING"
patterns: []
algorithms: ["hash-map-lookup"]
dataStructures: ["hash-table"]
templates: ["hash-map-template-ts"]
problems: ["two-sum", "contains-duplicate", "valid-anagram", "group-anagrams", "top-k-frequent-elements"]
prerequisites: ["array", "hash-table"]
relatedPatterns: []
status: "PUBLISHED"
---

# Hash Map Lookup Pattern

## Definition

Using a hash map (hash table) to store key-value mappings for O(1) average-case lookups, insertions, and deletions. This pattern trades space for time by maintaining a lookup structure alongside or instead of linear scans.

## When to Use

- Need to find if an element exists in a collection (O(1) vs O(n) linear search)
- Need to count frequencies of elements
- Need to map values to indices or other metadata
- Two-sum style problems: find complement in constant time
- Grouping elements by a computed key (anagrams, frequencies)
- Caching/memoization of computed results

## Why It Works

Hash functions distribute keys uniformly across buckets, giving O(1) average-case operations. Collisions are handled via chaining or open addressing. The key insight is that we can "precompute" information and store it for instant retrieval, transforming O(n) searches into O(1) lookups.

## Recognition Signals

- **Structural**: "Find pair with sum X" → complement = X - current, check if seen
- **Structural**: "Count occurrences of each element" → map element → count
- **Structural**: "Check if element exists" → hash set/map for O(1) membership
- **Structural**: "Group by property" → hash map with property as key, list as value
- **Keywords**: "frequency", "count", "seen before", "complement", "two-sum", "anagram"

## Invariant

At any point during iteration, the hash map contains exactly the information about all elements processed so far (indices, counts, or mappings).

## Strengths

- O(1) average case for insert, lookup, delete
- Flexible key types (strings, objects, custom)
- Enables single-pass algorithms for many problems
- Simple to implement and reason about

## Weaknesses

- O(n) worst case (pathological collisions or adversarial input)
- Extra O(n) space overhead
- No ordering of keys (use TreeMap if ordered iteration needed)
- Hash function quality critical for performance

## Common Failure Modes

1. **Forgetting to check before inserting**: In Two Sum, checking map *after* inserting causes self-pairing
2. **Wrong key type**: Using object reference instead of value for custom objects
3. **Not handling missing keys**: Map.get() returns undefined, not 0
4. **Ignoring space constraints**: O(n) space may be prohibitive for memory-limited environments

## Generalizations

- **Frequency Count**: Map element → count (extends lookup to counting)
- **Prefix Sum + Hash Map**: Map prefix sum → index for subarray sum problems
- **Multi-map**: Map key → list of values for grouping problems
- **Ordered Map**: TreeMap for range queries on keys

## Complexity

- Time: O(1) average for insert/lookup/delete
- Space: O(n) for storing n elements

## Example Problems

- **Two Sum**: Map value → index, check for target - current
- **Contains Duplicate**: Set for O(1) membership test
- **Valid Anagram**: Frequency maps for both strings, compare
- **Group Anagrams**: Map sorted string → list of anagrams
- **Top K Frequent**: Frequency map + heap/bucket sort