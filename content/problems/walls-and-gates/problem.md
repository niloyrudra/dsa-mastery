---
id: "walls-and-gates"
title: "Walls and Gates"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/walls-and-gates/"
patterns: ["graph-bfs", "graph-dfs", "geometry-fundamentals"]
algorithms: ["bfs", "dfs"]
dataStructures: ["graph", "array"]
templates: ["graph-template-ts"]
prerequisites: ["graph", "array"]
relatedProblems: ["number-of-islands", "rotting-oranges", "surrounded-regions", "pacific-atlantic-water-flow", "surrounded-regions", "swim-in-rising-water"]
status: "PUBLISHED"
orderIndex: 31
reasonForSelection: "Multi-source BFS from all gates; distance propagation"
---

# Problem Statement

You are given an `m x n` grid `rooms` initialized with these three possible values:

- `-1` A wall or an obstacle.
- `0` A gate.
- `INF` Infinity means an empty room. We use the value `2³¹ - 1 = 2147483647` to represent `INF` as you may assume that the distance to a gate is less than `2147483647`.

Fill each empty room with the distance to its **nearest gate**. If it is impossible to reach a gate, it should be filled with `INF`.

## Constraints

- m == rooms.length
- n == rooms[i].length
- 1 ≤ m, n ≤ 250
- rooms[i][j] is `-1`, `0`, or `2147483647`.

## Examples

**Example 1:**
```
Input: rooms = [[2147483647,-1,0,2147483647],[2147483647,2147483647,2147483647,-1],[2147483647,-1,2147483647,-1],[0,-1,2147483647,2147483647]]
Output: [[3,-1,0,1],[2,2,1,-1],[1,-1,2,-1],[0,1,2,-1]]
```

**Example 2:**
```
Input: rooms = [[-1]]
Output: [[-1]]
```

**Example 3:**
```
Input: rooms = [[2147483647]]
Output: [[2147483647]]
```

## Variations

### Variation 1: Rotting Oranges
**Name**: Rotting Oranges

**What Changed**: Multi-source BFS from rotten oranges.

**Original Approach Works**: Same multi-source BFS.

**What Breaks**: Minutes vs distance to gate.

**New Constraint**: Return minutes to rot all.

**Algorithm Changes**: Multi-source BFS, track levels.

**Complexity Changes**: Time O(m*n), Space O(m*n).

### Variation 2: Surrounded Regions
**Name**: Surrounded Regions

**What Changed**: Capture surrounded 'O's.

**Original Approach Works**: Boundary-first DFS/BFS.

**What Breaks**: Flip vs distance fill.

**New Constraint**: Flip surrounded 'O's to 'X'.

**Algorithm Changes**: Boundary DFS, mark 'O' -> '#', then flip.

**Complexity Changes**: Time O(m*n), Space O(m*n).