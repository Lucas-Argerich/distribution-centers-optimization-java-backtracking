package abstractos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class MinHeap implements Iterable<Arista> {
  private List<Arista> heap;

  public MinHeap() {
    this.heap = new ArrayList<>();
  }

  public void insert(Arista value) {
    heap.add(value);
    heapifyUp();
  }

  public Arista extractMin() {
    if (isEmpty()) {
      throw new IllegalStateException("MinHeap vacio.");
    }

    Arista minValue = heap.get(0);
    int lastIndex = heap.size() - 1;
    heap.set(0, heap.get(lastIndex));
    heap.remove(lastIndex);
    heapifyDown();

    return minValue;
  }

  public boolean isEmpty() {
    return heap.isEmpty();
  }

  private void heapifyUp() {
    int currentIndex = heap.size() - 1;

    while (currentIndex > 0) {
      int parentIndex = (currentIndex - 1) / 2;
      if (heap.get(currentIndex).compareTo(heap.get(parentIndex)) < 0) {
        swap(currentIndex, parentIndex);
        currentIndex = parentIndex;
      } else {
        break;
      }
    }
  }

  private void heapifyDown() {
    int currentIndex = 0;
    int leftChildIndex;

    while (true) {
      leftChildIndex = 2 * currentIndex + 1;
      int rightChildIndex = leftChildIndex + 1;

      if (leftChildIndex >= heap.size()) {
        break;
      }

      int minChildIndex = (rightChildIndex < heap.size() &&
          heap.get(rightChildIndex).compareTo(heap.get(leftChildIndex)) < 0) ? rightChildIndex : leftChildIndex;

      if (heap.get(currentIndex).compareTo(heap.get(minChildIndex)) > 0) {
        swap(currentIndex, minChildIndex);
        currentIndex = minChildIndex;
      } else {
        break;
      }
    }
  }

  private void swap(int i, int j) {
    Arista temp = heap.get(i);
    heap.set(i, heap.get(j));
    heap.set(j, temp);
  }

  @Override
  public Iterator<Arista> iterator() {
    return new HeapIterator();
  }

  private class HeapIterator implements Iterator<Arista> {
    private int currentIndex = 0;

    @Override
    public boolean hasNext() {
      return currentIndex < heap.size();
    }

    @Override
    public Arista next() {
      if (!hasNext()) {
        throw new NoSuchElementException("MinHeap vacio.");
      }

      return heap.get(currentIndex++);
    }
  }
}