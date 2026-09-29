---
id: "course-schedule"
title: "Course Schedule"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/course-schedule/"
patterns: ["topological-sort", "graph-dfs", "graph-bfs"]
algorithms: ["topological-sort", "dfs", "bfs"]
dataStructures: ["graph", "queue"]
templates: ["graph-template-ts"]
prerequisites: ["graph", "queue", "dfs"]
relatedProblems: ["course-schedule-ii", "alien-dictionary", "sequence-reconstruction", "parallel-courses"]
status: "PUBLISHED"
orderIndex: 53
reasonForSelection: "Cycle detection with topological sort; Kahn's or DFS with state"
---

# Problem Statement

There are a total of `numCourses` courses you have to take, labeled from `0` to `numCourses - 1`. You are given an array `prerequisites` where `prerequisites[i] = [a, b]` indicates that you **must** take course `b` first if you want to take course `a`.

Return `true` if you can finish all courses. Otherwise, return `false`.

## Constraints

- 1 ≤ numCourses ≤ 2000
- 0 ≤ prerequisites.length ≤ 5000
- prerequisites[i].length == 2
- 0 ≤ a, b < numCourses
- All the pairs prerequisites[i] are **unique**.

## Examples

**Example 1:**
```
Input: numCourses = 2, prerequisites = [[1,0]]
Output: true
Explanation: There are a total of 2 courses to take. To take course 1 you should have finished course 0. So it is possible.
```

**Example 2:**
```
Input: numCourses = 2, prerequisites = [[1,0],[0,1]]
Output: false
Explanation: There are a total of 2 courses to take. To take course 1 you should have finished course 0, and to take course 0 you should also have finished course 1. So it is impossible.
```

## Variations

### Variation 1: Course Schedule II
**Name**: Course Schedule II

**What Changed**: Return the ordering of courses instead of just true/false.

**Original Approach Works**: Same topological sort, but collect order.

**What Breaks**: Need to track and return the order.

**New Constraint**: Return topological ordering or empty array if cycle.

**Algorithm Changes**: Collect nodes in order during Kahn's or reverse post-order DFS.

**Complexity Changes**: Time O(V+E), Space O(V+E).

### Variation 2: Alien Dictionary
**Name**: Alien Dictionary

**What Changed**: Determine alphabet order from sorted words.

**Original Approach Works**: Build graph from word pairs, topological sort.

**What Breaks**: Implicit graph from word comparisons.

**New Constraint**: Return string of unique letters in alien order.

**Algorithm Changes**: Compare adjacent words to find edges, then topological sort.

**Complexity Changes**: Time O(C) where C = total characters, Space O(1) for 26 letters.