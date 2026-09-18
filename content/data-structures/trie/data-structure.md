---
id: "trie"
name: "Trie (Prefix Tree)"
description: "Tree-like structure for storing strings. Each node represents a character. Paths from root form prefixes."
operations: '{"insert": "O(L)", "search": "O(L)", "delete": "O(L)", "prefix_search": "O(L)", "starts_with": "O(L)"}'
why_useful_here: "Best for prefix matching, autocomplete, dictionary lookup. O(L) operations where L is string length."
tradeoffs: "High memory overhead (pointers per node). Only for string/character data. Not cache-friendly. Complex deletion."
alternatives: "Ternary Search Tree, Radix Tree, Hash Table, Suffix Array"
status: "PUBLISHED"
---
# Trie (Prefix Tree)

## Overview
Trie stores strings as paths from root. Each edge represents a character. Shared prefixes = shared nodes = space savings.

## Structure
- **Root**: Empty string
- **Nodes**: Character + children map + isEndOfWord flag
- **Edges**: Labeled with characters
- **Depth**: String length

## Operations
| Operation | Time Complexity |
|-----------|----------------|
| Insert | O(L) |
| Search | O(L) |
| Delete | O(L) |
| Prefix Search | O(L) |
| Starts With | O(L) |

Where L = string length.

## Node Structure
```typescript
class TrieNode {
    children: Map<char, TrieNode> // or array[26]
    isEndOfWord: boolean
    count?: number // for frequency
}
```

## Applications
- **Autocomplete**: Prefix suggestions
- **Spell Check**: Dictionary validation
- **IP Routing**: Longest prefix match
- **Word Search II**: Board + dictionary
- **Maximum XOR**: Bitwise trie
- **Substring Problems**: Suffix trie

## Variants
- **Compressed Trie (Radix Tree)**: Merge single-child chains
- **Ternary Search Tree**: BST + Trie hybrid
- **Bitwise Trie**: For integer XOR problems
- **Suffix Trie/Tree**: All suffixes

## When to Use
- Prefix-based operations
- Dictionary with autocomplete
- String matching with wildcards
- IP routing tables
- Bitwise optimization problems