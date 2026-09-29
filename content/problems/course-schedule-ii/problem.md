---
id: "course-schedule-ii"
title: "Course Schedule II"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/course-schedule-ii/"
patterns: ["topological-sort", "graph-dfs", "graph-bfs"]
algorithms: ["topological-sort", "dfs", "bfs"]
dataStructures: ["graph", "queue"]
templates: ["graph-template-ts"]
prerequisites: ["graph", "queue", "dfs"]
relatedProblems: ["course-schedule", "alien-dictionary", "sequence-reconstruction", "parallel-courses"]
status: "PUBLISHED"
orderIndex: 54
reasonForSelection: "Kahn's algorithm returns actual topological order"
---

# Problem Statement

There are a total of `numCourses` courses you have to take, labeled from `0` to `numCourses - 1`. You are given an array `prerequisites` where `prerequisites[i] = [a, b]` indicates that you **must** take course `b` first if you want to take course `a`.

Return the ordering of courses you should take to finish all courses. If there are many valid answers, return **any** of them. If it is impossible to finish all courses, return an empty array.

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
Output: [0,1]
Explanation: There are a total of 2 courses to take. To take course 1 you should have finished course 0. So the correct course order is [0,1].
```

**Example 2:**
```
Input: numCourses = 4, prerequisites = [[1,0],[2,0],[3,1],[3,2]]
Output: [0,2,1,3] or [0,1,2,3]
Explanation: There are a total of 4 courses to take. To take course 3 you should have finished both courses 1 and 2. Both courses 1 and 2 should be finished after course 0. So one correct answer is [0,1,2,3]. Another correct answer is [0,2,1,3].
```

**Example 3:**
```
Input: numCourses = 1, prerequisites = []
Output: [0]
```

## Variations

### Variation 1: Course Schedule
**Name**: Course Schedule

**What Changed**: Return boolean instead of ordering.

**Original Approach Works**: Same topological sort, just check if all nodes processed.

**What Breaks**: Don't need to collect order.

**New Constraint**: Return true/false.

**Algorithm Changes**: Count processed nodes, compare with numCourses.

**Complexity Changes**: Time O(V+E), Space O(V+E).

### Variation 2: Alien Dictionary
**Name**: Alien Dictionary

**What Changed**: Determine alphabet order from sorted words.

**Original Approach Works**: Build graph from word pairs, topological sort.

**What Breaks**: Implicit graph from word comparisons.

**New Constraint**: Return string of unique letters in alien order.

**Algorithm Changes**: Compare adjacent words to find edges, then topological sort.

**Complexity Changes**: Time O(C) where C = total characters, Space O(1) for 26 letters.