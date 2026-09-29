---
id: "time-based-key-value-store"
title: "Time Based Key-Value Store"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/time-based-key-value-store/"
patterns: ["binary-search-on-answer", "binary-search-boundary", "hash-map-lookup"]
algorithms: ["binary-search"]
dataStructures: ["hash-table", "array"]
templates: ["binary-search-template-ts", "hash-map-template-ts"]
prerequisites: ["array", "binary-search", "hash-table"]
relatedProblems: ["find-first-and-last-position-of-element-in-sorted-array", "snapshot-array", "design-in-memory-file-system", "lru-cache"]
status: "PUBLISHED"
orderIndex: 24
reasonForSelection: "Binary search on timestamp; map of lists with binary search"
---

# Problem Statement

Design a time-based key-value data structure that can store multiple values for the same key at different time stamps and retrieve the key's value at a certain timestamp.

Implement the `TimeMap` class:

- `TimeMap()` Initializes the object of the data structure.
- `void set(String key, String value, int timestamp)` Stores the key `key` with the value `value` at the given time `timestamp`.
- `String get(String key, int timestamp)` Returns a value such that `set` was called previously, with `timestamp_prev <= timestamp`. If there are multiple such values, it returns the one with the largest `timestamp_prev`. If there is no value, it returns `""`.

## Constraints

- 1 ≤ key.length, value.length ≤ 100
- key and value consist of lowercase English letters and digits.
- 1 ≤ timestamp ≤ 10⁷
- All the timestamps `timestamp` of `set` are strictly increasing.
- At most 2 × 10⁵ calls will be made to `set` and `get`.

## Examples

**Example 1:**
```
Input:
["TimeMap","set","set","get","get","get","get","get"]
[[],["foo","bar",1],["foo","bar2",4],["foo",1],["foo",3],["foo",4],["foo",5]]
Output:
[null,null,null,"bar","bar","bar2","bar2"]
Explanation:
TimeMap timeMap = new TimeMap();
timeMap.set("foo", "bar", 1);  // store the key "foo" and value "bar" along with timestamp = 1
timeMap.set("foo", "bar2", 4); // store the key "foo" and value "bar2" along with timestamp = 4
timeMap.get("foo", 1);         // return "bar"
timeMap.get("foo", 3);         // return "bar"
timeMap.get("foo", 4);         // return "bar2"
timeMap.get("foo", 5);         // return "bar2"
```

## Variations

### Variation 1: Snapshot Array
**Name**: Snapshot Array

**What Changed**: Array with snapshots instead of key-value.

**Original Approach Works**: Binary search on timestamps.

**What Breaks**: Array indices vs keys.

**New Constraint**: Snapshots of array at points in time.

**Algorithm Changes**: Array of snapshots per index, binary search.

**Complexity Changes**: Time O(log n) set/get, Space O(n * snapshots).

### Variation 2: LRU Cache
**Name**: LRU Cache

**What Changed**: Least Recently Used eviction.

**Original Approach Works**: Doubly linked list + hash map.

**What Breaks**: LRU vs time-based.

**New Constraint**: O(1) get/put with LRU eviction.

**Algorithm Changes**: Hash map + doubly linked list.

**Complexity Changes**: Time O(1), Space O(capacity).