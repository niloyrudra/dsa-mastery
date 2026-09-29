---
id: "design-add-and-search-words-data-structure"
title: "Design Add and Search Words Data Structure"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/design-add-and-search-words-data-structure/"
patterns: ["trie-fundamentals"]
algorithms: []
dataStructures: ["trie"]
templates: []
prerequisites: ["trie"]
relatedProblems: ["implement-trie-prefix-tree", "word-search-ii", "maximum-xor"]
status: "PUBLISHED"
orderIndex: 37
reasonForSelection: "Trie with DFS for wildcard '.' search; recursion on children"
---

# Problem Statement

Design a data structure that supports adding new words and finding if a string matches any previously added string.

Implement the `WordDictionary` class:

- `WordDictionary()` Initializes the object.
- `void addWord(word)` Adds `word` to the data structure, it can be matched later.
- `bool search(word)` Returns `true` if there is any string in the data structure that matches `word` or `false` otherwise. `word` may contain dots `'.'` where dots can be matched with any letter.

## Constraints

- 1 ≤ word.length ≤ 25
- `word` in `addWord` consists of lowercase English letters.
- `word` in `search` consist of '.' or lowercase English letters.
- At most 10⁴ calls will be made to `addWord` and `search`.

## Examples

**Example 1:**
```
Input:
["WordDictionary","addWord","addWord","addWord","search","search","search","search"]
[[],["bad"],["dad"],["mad"],["pad"],["bad"],[".ad"],["b.."]]
Output:
[null,null,null,null,false,true,true,true]
Explanation:
WordDictionary wordDictionary = new WordDictionary();
wordDictionary.addWord("bad");
wordDictionary.addWord("dad");
wordDictionary.addWord("mad");
wordDictionary.search("pad"); // return False
wordDictionary.search("bad"); // return True
wordDictionary.search(".ad"); // return True
wordDictionary.search("b.."); // return True
```

## Variations

### Variation 1: Implement Trie (Prefix Tree)
**Name**: Implement Trie (Prefix Tree)

**What Changed**: No wildcard search; standard trie operations.

**Original Approach Works**: Same trie structure, simpler search.

**What Breaks**: No '.' wildcard handling.

**New Constraint**: Insert, search, startsWith operations.

**Algorithm Changes**: Standard trie insert/search/prefix.

**Complexity Changes**: Time O(L) per operation, Space O(ALPHABET * N * L).

### Variation 2: Word Search II
**Name**: Word Search II

**What Changed**: Find all words from dictionary on 2D board.

**Original Approach Works**: Trie for dictionary, DFS on board.

**What Breaks**: 2D grid traversal with backtracking.

**New Constraint**: Return all words found on board.

**Algorithm Changes**: Trie + DFS on board, mark visited, backtrack.

**Complexity Changes**: Time O(m*n*4^L), Space O(ALPHABET * N * L).