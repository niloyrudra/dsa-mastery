---
id: "task-scheduler"
title: "Task Scheduler"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/task-scheduler/"
patterns: ["priority-queue-applications", "greedy-fundamentals", "interval-fundamentals"]
algorithms: []
dataStructures: ["heap", "queue"]
templates: ["heap-template-ts"]
prerequisites: ["heap", "queue"]
relatedProblems: ["design-twitter", "hand-of-straights", "minimum-intervals-to-remove", "non-overlapping-intervals", "last-stone-weight"]
status: "PUBLISHED"
orderIndex: 38
reasonForSelection: "Max-heap with cooling queue; greedy most frequent first"
---

# Problem Statement

Given a characters array `tasks`, representing the tasks a CPU needs to do, where each letter represents a different task. Tasks could be done in any order. Each task is done in one unit of time. For each unit of time, the CPU could complete either one task or just be idle.

However, there is a non-negative integer `n` that represents the cooldown period between two **same** tasks (the same letter in the array), that is, there must be at least `n` units of time between any two same tasks.

Return the **least number of units of time** that the CPU will take to finish all the given tasks.

## Constraints

- 1 ≤ tasks.length ≤ 10⁴
- tasks[i] is upper-case English letter.
- 0 ≤ n ≤ 100

## Examples

**Example 1:**
```
Input: tasks = ["A","A","A","B","B","B"], n = 2
Output: 8
Explanation: 
A -> B -> idle -> A -> B -> idle -> A -> B
There is at least 2 units of time between any two same tasks.
```

**Example 2:**
```
Input: tasks = ["A","A","A","B","B","B"], n = 0
Output: 6
```

## Variations

### Variation 1: Hand of Straights
**Name**: Hand of Straights

**What Changed**: Group consecutive cards instead of scheduling.

**Original Approach Works**: Greedy with min-heap/sorted map.

**What Breaks**: Consecutive groups vs cooldown.

**New Constraint**: Group size fixed.

**Algorithm Changes**: Min-heap of values, form groups.

**Complexity Changes**: Time O(n log n), Space O(n).

### Variation 2: Design Twitter
**Name**: Design Twitter

**What Changed**: News feed with followees.

**Original Approach Works**: Max-heap of tweet timestamps.

**What Breaks**: Social network vs scheduling.

**New Constraint**: Get 10 most recent tweets.

**Algorithm Changes**: Max-heap of tweets, followee tracking.

**Complexity Changes**: Time O(k log k) for getNewsFeed.