/*
This is free and unencumbered software released into the public domain.

Anyone is free to copy, modify, publish, use, compile, sell, or
distribute this software, either in source code form or as a compiled
binary, for any purpose, commercial or non-commercial, and by any
means.

In jurisdictions that recognize copyright laws, the author or authors
of this software dedicate any and all copyright interest in the
software to the public domain. We make this dedication for the benefit
of the public at large and to the detriment of our heirs and
successors. We intend this dedication to be an overt act of
relinquishment in perpetuity of all present and future rights to this
software under copyright law.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
OTHER DEALINGS IN THE SOFTWARE.

For more information, please refer to <http://unlicense.org/>
 */
package jkumensa.api;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * This is a Set that was built specifically for AllergyCodes which are just
 * upper-case characters. However, it can easily be used for other purposes too
 * where a set of characters is required.
 * <p>
 * Internally, it uses a single int to represent the stored chars which makes
 * the interactions and manipulations efficient and also uses minimal memory.
 * </p>
 */
public class AllergyCodeSet implements Set<Character> {
    private static final Character[] all = new Character['Z' - 'A'];

    static {
        for (int i = 0; i < all.length; i++) {
            all[i] = (char) ('A' + i);
        }
    }

    int bits = 0;

    @Override
    public int size() {
        return Integer.bitCount(bits);
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        if (!(o instanceof Character)) {
            return false;
        }
        char c = (Character) o;
        if (isAllowed(c)) {
            return contains(c);
        } else {
            return false;
        }
    }

    public boolean contains(char c) {
        if (isAllowed(c)) {
            return (bits & (1 << getIndexOf(c))) != 0;
        } else {
            return false;
        }
    }

    @Override
    public CharIterator iterator() {
        return new CharIterator(bits);
    }

    public static class CharIterator implements Iterator<Character> {
        int bits;

        CharIterator(int bits) {
            this.bits = bits;
        }

        @Override
        public boolean hasNext() {
            return bits > 0;
        }

        @Override
        public Character next() {
            int z = Integer.numberOfTrailingZeros(bits);
            bits &= ~(1 << z);
            return all[z];
        }

        public char nextChar() {
            int z = Integer.numberOfTrailingZeros(bits);
            bits &= ~(1 << z);
            return (char) ('A' + z);
        }
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean add(Character e) {
        return add((char) e);
    }

    public boolean add(char c) {
        checkAllowed(c);
        boolean r = contains(c);
        bits |= (1 << getIndexOf(c));
        return r;
    }

    @Override
    public boolean remove(Object o) {
        if (!(o instanceof Character)) {
            return false;
        }
        char c = (Character) o;
        if (isAllowed(c)) {
            return remove(c);
        } else {
            return false;
        }
    }

    public boolean remove(char c) {
        if (isAllowed(c)) {
            boolean r = contains(c);
            bits &= ~(1 << getIndexOf(c));
            return r;
        } else {
            return false;
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (c instanceof AllergyCodeSet) {
            AllergyCodeSet s = (AllergyCodeSet) c;
            return bits == s.bits;
        } else {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    @Override
    public boolean addAll(Collection<? extends Character> c) {
        if (c instanceof AllergyCodeSet) {
            int before = bits;
            bits |= ((AllergyCodeSet) c).bits;
            return bits != before;
        } else if (c.isEmpty()) {
            return false;
        } else {
            boolean changed = false;
            for (Character e : c) {
                char ch = e;
                changed |= add(ch);
            }
            return changed;
        }
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean changed = false;
        for (Object o : c) {
            if (o instanceof Character) {
                char ch = (Character) o;
                if (isAllowed(ch)) {
                    changed = true;
                    remove(ch);
                }
            }
        }
        return changed;
    }

    @Override
    public void clear() {
        bits = 0;
    }

    private int getIndexOf(char c) {
        return c - 'A';
    }

    private void checkAllowed(char c) {
        if (!isAllowed(c)) {
            throw new IllegalArgumentException(c + " is not allowed!");
        }
    }

    private boolean isAllowed(char c) {
        return 'A' <= c && c <= 'Z';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AllergyCodeSet) {
            return bits == ((AllergyCodeSet) obj).bits;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + this.bits;
        return hash;
    }
}
