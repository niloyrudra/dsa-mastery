---
id: "alien-dictionary"
title: "Alien Dictionary"
difficulty: "HARD"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/alien-dictionary/"
patterns: ["topological-sort", "graph-dfs", "graph-bfs"]
algorithms: ["topological-sort", "dfs", "bfs"]
dataStructures: ["graph", "hash-table"]
templates: ["graph-template-ts"]
prerequisites: ["graph", "topological-sort"]
relatedProblems: ["course-schedule", "course-schedule-ii", "sequence-reconstruction"]
status: "PUBLISHED"
orderIndex: 70
reasonForSelection: "Topological sort on character dependencies; determines alphabet order"
---

# Problem Statement

There is a new alien language that uses the English alphabet. However, the order among the letters is unknown to you.

You are given a list of strings `words` from the alien language's dictionary, where the strings in `words` are **sorted lexicographically** by the rules of this new language.

Derive the order of letters in this language, and return a string of the unique letters in the new alien language sorted in **lexicographically increasing order** by the new language's rules. If there is no solution, return `""`. If there are multiple solutions, return **any of them**.

## Constraints

- 1 ≤ words.length ≤ 100
- 1 ≤ words[i].length ≤ 100
- `words[i]` consists of only lowercase English letters.

## Examples

**Example 1:**
```
Input: words = ["wrt","wrf","er","ett","rftt"]
Output: "wertf"
```

**Example 2:**
```
Input: words = ["z","x"]
Output: "zx"
```

**Example 3:**
```
Input: words = ["z","x","z"]
Output: ""
Explanation: The order is invalid, so return "".
```

## Variations

### Variation 1: Course Schedule
**Name**: Course Schedule

**What Changed**: Course prerequisites instead of character ordering.

**Original Approach Works**: Same topological sort on directed graph.

**What Breaks**: Different domain, same algorithm.

**New Constraint**: Detect cycle in course prerequisites.

**Algorithm Changes**: Same - Kahn's algorithm or DFS with cycle detection.

**Complexity Changes**: Time O(V+E), Space O(V+E).

### Variation 2: Sequence Reconstruction
**Name**: Sequence Reconstruction

**What Changed**: Check if original sequence can be uniquely reconstructed from subsequences.

**Original Approach Works**: Topological sort with uniqueness check.

**What Breaks**: Must verify unique topological ordering.

**New Constraint**: Return true only if unique reconstruction possible.

**Algorithm Changes**: Check if at each step only one node has indegree 0.

**Complexity Changes**: Time O(V+E), Space O(V+E).