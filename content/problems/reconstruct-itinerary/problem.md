---
id: "reconstruct-itinerary"
title: "Reconstruct Itinerary"
difficulty: "HARD"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/reconstruct-itinerary/"
patterns: ["topological-sort", "graph-dfs"]
algorithms: ["topological-sort", "dfs"]
dataStructures: ["graph", "heap"]
templates: ["graph-template-ts"]
prerequisites: ["graph", "topological-sort"]
relatedProblems: ["course-schedule", "course-schedule-ii", "alien-dictionary", "sequence-reconstruction"]
status: "PUBLISHED"
orderIndex: 54
reasonForSelection: "Hierholzer's algorithm for Eulerian path; lexical order"
---

# Problem Statement

You are given a list of airline `tickets` where `tickets[i] = [from, to]` represents the departure and the arrival airports of one flight. Reconstruct the itinerary in order and return it.

All of the tickets belong to a man who departs from `"JFK"`, thus the itinerary must begin with `"JFK"`. If there are multiple valid itineraries, you should return the itinerary that has the smallest lexical order when read as a single string.

## Constraints

- 1 ≤ tickets.length ≤ 300
- tickets[i].length == 2
- from.length == 3
- to.length == 3
- from != to
- All tickets are **unique**.

## Examples

**Example 1:**
```
Input: tickets = [["MUC","LHR"],["JFK","MUC"],["SFO","SJC"],["LHR","SFO"]]
Output: ["JFK","MUC","LHR","SFO","SJC"]
```

**Example 2:**
```
Input: tickets = [["JFK","SFO"],["JFK","ATL"],["SFO","ATL"],["ATL","JFK"],["ATL","SFO"]]
Output: ["JFK","ATL","JFK","SFO","ATL","SFO"]
Explanation: Another possible itinerary is ["JFK","SFO","ATL","JFK","ATL","SFO"] but it is larger in lexical order.
```

## Variations

### Variation 1: Course Schedule
**Name**: Course Schedule

**What Changed**: Course prerequisites instead of tickets.

**Original Approach Works**: Topological sort (Kahn's or DFS).

**What Breaks**: Different domain.

**New Constraint**: Return valid course order.

**Algorithm Changes**: Kahn's algorithm or DFS with cycle detection.

**Complexity Changes**: Time O(V+E), Space O(V+E).

### Variation 2: Alien Dictionary
**Name**: Alien Dictionary

**What Changed**: Determine alphabet order from sorted words.

**Original Approach Works**: Build graph, topological sort.

**What Breaks**: Implicit graph from word pairs.

**New Constraint**: Return string of unique letters in order.

**Algorithm Changes**: Compare adjacent words for edges, topological sort.

**Complexity Changes**: Time O(C) where C = total characters.