---
id: "heap-template-ts"
name: "Heap Template"
language: "TYPESCRIPT"
purpose: "Templates for min/max heap, priority queue, top-k, median finder"
when_to_reach_for_it: "Top k elements, merge k lists, task scheduler, median finder, Dijkstra"
prerequisites: "Understanding of heap property, array representation, O(log n) operations"
mental_model: "Complete binary tree in array. Parent at i, children at 2i+1, 2i+2. Bubble up/down to maintain heap property."
explanation: "MinHeap for smallest elements, MaxHeap for largest. Dual heap balances for median. Top-K uses min-heap of size k."
invariant: "MinHeap: parent <= children. MaxHeap: parent >= children. Dual: sizes differ by at most 1, all small <= all large."
common_modifications: "Custom comparator for objects. Indexed heap for decrease-key. Fibonacci heap for better theoretical bounds."
common_mistakes: "Wrong compare direction; not handling empty heap; forgetting to rebalance dual heap; using wrong heap type for min/max."
time_complexity: "O(log n) push/pop, O(1) peek, O(n) heapify"
space_complexity: "O(n)"
pattern: "heap-fundamentals"
status: "PUBLISHED"
---
# Heap Templates (TypeScript)

## MinHeap Class
```typescript
class MinHeap<T> {
    private heap: T[] = [];
    private compare: (a: T, b: T) => number;
    constructor(compare: (a: T, b: T) => number = (a, b) => a - b) { this.compare = compare; }
    size(): number { return this.heap.length; }
    peek(): T | undefined { return this.heap[0]; }
    push(val: T): void { this.heap.push(val); this.bubbleUp(this.heap.length - 1); }
    pop(): T | undefined {
        if (this.isEmpty()) return undefined;
        const top = this.heap[0];
        const end = this.heap.pop()!;
        if (!this.isEmpty()) { this.heap[0] = end; this.bubbleDown(0); }
        return top;
    }
    private bubbleUp(idx: number): void {
        while (idx > 0) {
            const p = Math.floor((idx - 1) / 2);
            if (this.compare(this.heap[idx], this.heap[p]) >= 0) break;
            [this.heap[idx], this.heap[p]] = [this.heap[p], this.heap[idx]];
            idx = p;
        }
    }
    private bubbleDown(idx: number): void {
        const n = this.heap.length;
        while (true) {
            let s = idx, l = 2 * idx + 1, r = 2 * idx + 2;
            if (l < n && this.compare(this.heap[l], this.heap[s]) < 0) s = l;
            if (r < n && this.compare(this.heap[r], this.heap[s]) < 0) s = r;
            if (s === idx) break;
            [this.heap[idx], this.heap[s]] = [this.heap[s], this.heap[idx]];
            idx = s;
        }
    }
}
```

## Top K Frequent
```typescript
function topKFrequent(nums: number[], k: number): number[] {
    const freq = new Map<number, number>();
    for (const n of nums) freq.set(n, (freq.get(n) || 0) + 1);
    const heap = new MinHeap<[number, number]>((a, b) => a[1] - b[1]);
    for (const [num, f] of freq) { heap.push([num, f]); if (heap.size() > k) heap.pop(); }
    return heap.heap.map(([num]) => num);
}
```

## Merge K Sorted Lists
```typescript
function mergeKLists(lists: (ListNode | null)[]): ListNode | null {
    const heap = new MinHeap<ListNode>((a, b) => a.val - b.val);
    for (const l of lists) if (l) heap.push(l);
    const dummy = new ListNode(0);
    let curr = dummy;
    while (!heap.isEmpty()) {
        const node = heap.pop()!;
        curr.next = node;
        curr = curr.next;
        if (node.next) heap.push(node.next);
    }
    return dummy.next;
}
```

## Median Finder (Dual Heap)
```typescript
class MedianFinder {
    private small = new MaxHeap<number>(); // lower half
    private large = new MinHeap<number>(); // upper half
    addNum(num: number): void {
        this.small.push(num);
        this.large.push(this.small.pop()!);
        if (this.small.size() < this.large.size()) this.small.push(this.large.pop()!);
    }
    findMedian(): number {
        if (this.small.size() > this.large.size()) return this.small.peek()!;
        return (this.small.peek()! + this.large.peek()!) / 2;
    }
}
```