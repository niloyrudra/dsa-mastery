---
id: "copy-list-with-random-pointer"
title: "Copy List with Random Pointer"
difficulty: "MEDIUM"
source: "LeetCode"
sourceUrl: "https://leetcode.com/problems/copy-list-with-random-pointer/"
patterns: ["linked-list-fundamentals", "hash-map-lookup"]
algorithms: []
dataStructures: ["linked-list", "hash-table"]
templates: []
prerequisites: ["linked-list", "hash-table"]
relatedProblems: ["reverse-linked-list", "clone-graph", "add-two-numbers"]
status: "PUBLISHED"
orderIndex: 9
reasonForSelection: "Hash map for original->copy mapping; O(1) space interleaving alternative"
---

# Problem Statement

A linked list of length `n` is given such that each node contains an additional random pointer, which could point to any node in the list, or `null`.

Construct a **deep copy** of the list. The deep copy should consist of exactly `n` brand new nodes, where each new node has its value set to the value of its corresponding original node. Both the `next` and `random` pointer of the new nodes should point to new nodes in the copied list such that the pointers in the original list and copied list represent the same list state. None of the pointers in the new list should point to nodes in the original list.

Return the head of the copied linked list.

The linked list is represented in the input/output as a list of `n` nodes. Each node is represented as a pair of `[val, random_index]` where:

- `val`: an integer representing `Node.val`
- `random_index`: the index of the node (range from `0` to `n-1`) that the `random` pointer points to, or `null` if it does not point to any node.

Your code will **only** be given the `head` of the original linked list.

## Constraints

- 0 ≤ n ≤ 1000
- -10⁴ ≤ Node.val ≤ 10⁴
- Node.random is `null` or points to some node in the linked list.

## Examples

**Example 1:**
```
Input: head = [[7,null],[13,0],[11,4],[10,2],[1,0]]
Output: [[7,null],[13,0],[11,4],[10,2],[1,0]]
```

**Example 2:**
```
Input: head = [[1,1],[2,1]]
Output: [[1,1],[2,1]]
```

**Example 3:**
```
Input: head = []
Output: []
```

## Variations

### Variation 1: Clone Graph
**Name**: Clone Graph

**What Changed**: Graph instead of linked list.

**Original Approach Works**: Hash map original -> copy, DFS/BFS.

**What Breaks**: Graph can have cycles; more complex structure.

**New Constraint**: Undirected graph with neighbors.

**Algorithm Changes**: DFS/BFS with hash map, clone neighbors.

**Complexity Changes**: Time O(V+E), Space O(V).

### Variation 2: Reverse Linked List
**Name**: Reverse Linked List

**What Changed**: Reverse list instead of copy.

**Original Approach Works**: Iterative pointer manipulation.

**What Breaks**: No random pointer to handle.

**New Constraint**: Return reversed list head.

**Algorithm Changes**: Three pointers (prev, curr, next).

**Complexity Changes**: Time O(n), Space O(1).