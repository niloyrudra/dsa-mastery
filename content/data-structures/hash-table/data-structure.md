---
id: "hash-table"
name: "Hash Table"
description: "Key-value storage with average O(1) operations using hash function to map keys to buckets."
operations: '{"access": "O(1) avg", "search": "O(1) avg", "insert": "O(1) avg", "delete": "O(1) avg"}'
why_useful_here: "Best for fast lookups and frequency counting. Constant time operations on average."
tradeoffs: "No ordering. Worst case O(n) with collisions. Extra space overhead. Load factor affects performance."
alternatives: "Tree Map, Trie, Bloom Filter"
status: "PUBLISHED"
---
# Hash Table

## Overview
Hash table maps keys to values using a hash function. Provides average O(1) for all basic operations.

## Operations
| Operation | Average | Worst Case |
|-----------|---------|------------|
| Insert | O(1) | O(n) |
| Search | O(1) | O(n) |
| Delete | O(1) | O(n) |

## Collision Resolution
- **Chaining**: Each bucket is a linked list
- **Open Addressing**: Probe for next empty slot (linear, quadratic, double hashing)

## Key Concepts
- **Hash Function**: Maps key to bucket index
- **Load Factor**: n/m (elements/buckets). Resize when > 0.75
- **Good Hash**: Uniform distribution, fast computation

## Strengths
- **Average O(1)** for all operations
- **Fast lookups** for exact matches
- **Flexible keys**: Any hashable type
- **Frequency counting**: Natural fit

## Weaknesses
- **No ordering**: Can't iterate in sorted order
- **Worst case O(n)**: All keys hash to same bucket
- **Memory overhead**: Empty buckets, pointers
- **No range queries**: Can't find keys in range

## When to Use
- Fast exact-match lookups
- Frequency counting
- Caching (LRU, LFU)
- Deduplication
- Two-sum style problems