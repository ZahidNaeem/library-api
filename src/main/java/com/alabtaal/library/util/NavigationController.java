package com.alabtaal.library.util;

import java.util.List;

public class NavigationController<T> {

  public boolean firstDisabled;
  public boolean previousDisabled;
  public boolean nextDisabled;
  public boolean lastDisabled;
  public T object = null;
  private List<T> list;
  private int indx;

  public NavigationController(List<T> list, int indx) {
    this.list = list;
    this.indx = indx;
    this.object = (T) list.get(indx);
  }

  public T getObject() {
    return object;
  }

  public boolean isFirstDisabled() {
    return firstDisabled;
  }

  public boolean isPreviousDisabled() {
    return previousDisabled;
  }

  public boolean isNextDisabled() {
    return nextDisabled;
  }

  public boolean isLastDisabled() {
    return lastDisabled;
  }

  public void resetNavigation() {
    this.firstDisabled = true;
    this.previousDisabled = true;
    this.nextDisabled = true;
    this.lastDisabled = true;
  }

  public T first() {
    resetNavigation();
    if (list.size() > 0) {
      indx = 0;
      object = (T) list.get(indx);

      if (list.size() > 1) {
        nextDisabled = false;
        lastDisabled = false;
      }
      return object;
    }
    return null;
  }

  public T previous() {
    resetNavigation();
    if (list.size() > 0 && indx > 0) {
      indx--;
      object = (T) list.get(indx);

      if (indx > 0) {
        firstDisabled = false;
        previousDisabled = false;
      }
      nextDisabled = false;
      lastDisabled = false;
      return object;
    }
    return null;
  }

  public T next() {
    resetNavigation();
    if (list.size() > 0 && indx < list.size() - 1) {
      indx++;
      object = (T) list.get(indx);

      if (indx < (list.size() - 1)) {
        nextDisabled = false;
        lastDisabled = false;
      }
      firstDisabled = false;
      previousDisabled = false;
      return object;
    }
    return null;
  }

  public T last() {
    resetNavigation();
    if (list.size() > 0) {
      indx = list.size() - 1;
      object = (T) list.get(indx);
      if (list.size() > 1) {
        firstDisabled = false;
        previousDisabled = false;
      }
      return object;
    }
    return null;
  }

  public T go(int indx) {
    resetNavigation();
    if (list.size() > 0) {
      if (indx >= list.size() || indx < 0) {
        return null;
      } else if (indx == 0) {
        return first();
      } else if (indx == list.size() - 1) {
        return last();
      } else {
        previous();
        return next();
      }
    }
    return null;
  }
}
