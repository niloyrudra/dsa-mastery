---
id: "word-search"
title: "Word Search"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/word-search/"
patterns: ["backtracking-fundamentals", "graph-dfs", "geometry-fundamentals"]
algorithms: ["dfs"]
dataStructures: ["graph", "array"]
templates: ["graph-template-ts"]
prerequisites: ["graph", "recursion", "array"]
relatedProblems: ["word-search-ii", "exist", "longest-word-in-dictionary", "palindrome-partitioning", "generate-parentheses"]
status: "PUBLISHED"
orderIndex: 57
reasonForSelection: "DFS on grid with backtracking; mark visited cells"
---

# Problem Statement

Given an `m x n` grid of characters `board` and a string `word`, return `true` if `word` exists in the grid.

The word can be constructed from letters of sequentially adjacent cells, where adjacent cells are horizontally or vertically neighboring. The same letter cell may not be used more than once.

## Constraints

- m == board.length
- n == board[i].length
- 1 ≤ m, n ≤ 6
- 1 ≤ word.length ≤ 15
- `board` and `word` consist of only lowercase and uppercase English letters.

## Examples

**Example 1:**
```
Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"
Output: true
```

**Example 2:**
```
Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "SEE"
Output: true
```

**Example 3:**
```
Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCB"
Output: false
```

## Variations

### Variation 1: Word Search II
**Name**: Word Search II

**What Changed**: Find all words from dictionary on board.

**Original Approach Works**: Trie + DFS on board.

**What Breaks**: Multiple words vs single word.

**New Constraint**: Return all words found.

**Algorithm Changes**: Trie for dictionary, DFS on board, mark visited.

**Complexity Changes**: Time O(m*n*4^L), Space O(ALPHABET * N * L).

### Variation 2: Exist
**Name**: Exist (same problem, different name)

**What Changed**: Same problem.

**Original Approach Works**: Same backtracking.

**What Breaks**: N/A.

**New Constraint**: N/A.

**Algorithm Changes**: N/A.

**Complexity Changes**: N/A.