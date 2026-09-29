---
id: "implement-trie-prefix-tree"
title: "Implement Trie (Prefix Tree)"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/implement-trie-prefix-tree/"
patterns: ["trie-fundamentals"]
algorithms: []
dataStructures: ["trie"]
templates: []
prerequisites: ["trie"]
relatedProblems: ["design-add-and-search-words-data-structure", "word-search-ii", "maximum-xor", "add-and-search-word"]
status: "PUBLISHED"
orderIndex: 36
reasonForSelection: "Basic trie implementation; insert, search, startsWith"
---

# Problem Statement

A **trie** (pronounced as "try") or **prefix tree** is a tree data structure used to efficiently store and retrieve keys in a dataset of strings. There are various applications of this data structure, such as autocomplete and spellchecker.

Implement the Trie class:

- `Trie()` Initializes the trie object.
- `void insert(String word)` Inserts the string `word` into the trie.
- `boolean search(String word)` Returns `true` if the string `word` is in the trie (i.e., was inserted before), and `false` otherwise.
- `boolean startsWith(String prefix)` Returns `true` if there is a previously inserted string `word` that has the prefix `prefix`, and `false` otherwise.

## Constraints

- 1 ≤ word.length, prefix.length ≤ 2000
- `word` and `prefix` consist only of lowercase English letters.
- At most 3 × 10⁴ calls in total will be made to `insert`, `search`, and `startsWith`.

## Examples

**Example 1:**
```
Input:
["Trie","insert","search","search","startsWith","insert","search"]
[[],["apple"],["apple"],["app"],["app"],["app"],["app"]]
Output:
[null,null,true,false,true,null,true]
Explanation:
Trie trie = new Trie();
trie.insert("apple");
trie.search("apple");   // return True
trie.search("app");     // return False
trie.startsWith("app"); // return True
trie.insert("app");
trie.search("app");     // return True
```

## Variations

### Variation 1: Design Add and Search Words Data Structure
**Name**: Design Add and Search Words Data Structure

**What Changed**: Add wildcard '.' search.

**Original Approach Works**: Same trie, DFS for wildcard.

**What Breaks**: Need to handle '.' matching any character.

**New Constraint**: Search supports '.' wildcard.

**Algorithm Changes**: DFS recursion on children for '.'.

**Complexity Changes**: Time O(L) for insert/search, O(26^L) worst for wildcard.

### Variation 2: Word Search II
**Name**: Word Search II

**What Changed**: Find all dictionary words on 2D board.

**Original Approach Works**: Trie for dictionary, DFS on board.

**What Breaks**: 2D grid traversal with backtracking.

**New Constraint**: Return all words found on board.

**Algorithm Changes**: Trie + DFS on board, mark visited, backtrack.

**Complexity Changes**: Time O(m*n*4^L), Space O(ALPHABET * N * L).