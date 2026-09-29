---
id: "trie-fundamentals"
name: "Trie Fundamentals"
category: "TRIE"
patterns: []
algorithms: []
dataStructures: ["trie"]
templates: []
problems: ["implement-trie-prefix-tree", "design-add-and-search-words-data-structure", "word-search-ii", "maximum-xor", "add-and-search-word", "palindrome-pairs"]
prerequisites: ["trie"]
relatedPatterns: ["hash-map-lookup", "backtracking-fundamentals", "graph-dfs"]
status: "PUBLISHED"
---
# Trie Fundamentals Pattern

## Definition
Prefix tree for efficient string storage and retrieval; supports prefix search, autocomplete, dictionary lookup.

## When to Use
- Prefix matching
- Autocomplete
- Dictionary with prefix search
- Word search II
- IP routing
- Finding maximum XOR

## Why It Works
Each node represents a character; paths from root represent strings/prefixes. Shared prefixes save space. O(L) operations where L is string length.

## Intuition
Build tree where each edge is a character. Terminal nodes mark end of words. Traverse for search/insert/delete.

## Invariant
Each node has up to 26 children (lowercase) or more for extended alphabets; paths from root to terminal nodes form complete words.

## Strengths
- O(L) for insert/search/delete regardless of dictionary size
- Efficient prefix queries
- Space efficient for shared prefixes

## Weaknesses
- High memory overhead (pointers per node)
- Only for string/character data
- Not cache-friendly
- Complex deletion

## Recognition Signals
- "implement trie"
- "prefix search"
- "autocomplete"
- "word search ii"
- "maximum xor"
- "add and search word"
- "dictionary with wildcards"

## Common Failure Modes
- Not marking end of word
- Incorrect handling of empty strings
- Memory leaks in manual memory languages
- Not optimizing for space (using arrays vs maps)
- Incorrect DFS in word search II

## Generalizations
Can use compressed trie (radix tree) for space; ternary search tree as alternative; bitwise trie for integer XOR problems.

## Complexity
- Time: O(L) per operation where L is string length
- Space: O(ALPHABET_SIZE * N * L) worst case