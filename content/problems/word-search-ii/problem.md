---
id: "word-search-ii"
title: "Word Search II"
difficulty: "HARD"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/word-search-ii/"
patterns: ["trie-fundamentals", "backtracking-fundamentals", "graph-dfs"]
algorithms: []
dataStructures: ["trie", "graph", "array"]
templates: ["graph-template-ts"]
prerequisites: ["trie", "graph", "array", "recursion"]
relatedProblems: ["word-search", "design-add-and-search-words-data-structure", "implement-trie-prefix-tree", "maximum-xor", "palindrome-pairs"]
status: "PUBLISHED"
orderIndex: 38
reasonForSelection: "Trie + DFS on board; prune trie during search for efficiency"
---

# Problem Statement

Given an `m x n` board of characters and a list of strings `words`, return all words on the board.

Each word must be constructed from letters of sequentially adjacent cells, where adjacent cells are horizontally or vertically neighboring. The same letter cell may not be used more than once in a word.

## Constraints

- m == board.length
- n == board[i].length
- 1 ≤ m, n ≤ 12
- 1 ≤ words.length ≤ 3 × 10⁴
- 1 ≤ words[i].length ≤ 10
- `board` and `words[i]` consist of only lowercase English letters.
- All the strings of `words` are **unique**.

## Examples

**Example 1:**
```
Input: board = [["o","a","a","n"],["e","t","a","e"],["i","h","k","r"],["i","f","l","v"]], words = ["oath","pea","eat","rain"]
Output: ["eat","oath"]
```

**Example 2:**
```
Input: board = [["a","b"],["c","d"]], words = ["abc"]
Output: []
```

## Variations

### Variation 1: Word Search
**Name**: Word Search

**What Changed**: Single word search.

**Original Approach Works**: DFS with backtracking.

**What Breaks**: Single word vs multiple.

**New Constraint**: Return true/false for one word.

**Algorithm Changes**: DFS with backtracking.

**Complexity Changes**: Time O(m*n*4^L), Space O(L).

### Variation 2: Implement Trie (Prefix Tree)
**Name**: Implement Trie (Prefix Tree)

**What Changed**: Basic trie operations.

**Original Approach Works**: Same trie structure.

**What Breaks**: No board traversal.

**New Constraint**: Insert, search, startsWith.

**Algorithm Changes**: Trie insert/search/prefix.

**Complexity Changes**: Time O(L), Space O(ALPHABET * N * L).