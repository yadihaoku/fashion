/*
 * Copyright (C) 2014 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.collect;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.text.Segment;

import okio.ByteString;


/**
 * A collection of bytes in memory.
 *
 * <p><strong>Moving data from one buffer to another is fast.</strong> Instead
 * of copying bytes from one place in memory to another, this class just changes
 * ownership of the underlying byte arrays.
 *
 * <p><strong>This buffer grows with your data.</strong> Just like ArrayList,
 * each buffer starts small. It consumes only the memory it needs to.
 *
 * <p><strong>This buffer pools its byte arrays.</strong> When you allocate a
 * byte array in Java, the runtime must zero-fill the requested array before
 * returning it to you. Even if you're going to write over that space anyway.
 * This class avoids zero-fill and GC churn by pooling byte arrays.
 */
public final class Buffer {
  private static final byte[] DIGITS =
      { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
  static final int REPLACEMENT_CHARACTER = '\ufffd';

  long size;

  public Buffer() {
  }

  /** Returns the number of bytes currently in this buffer. */
  public long size() {
    return size;
  }

   public Buffer buffer() {
    return this;
  }

   public OutputStream outputStream() {
    return new OutputStream() {
       public void write(int b) {
      }

       public void write(byte[] data, int offset, int byteCount) {
      }

       public void flush() {
      }

       public void close() {
      }

       public String toString() {
        return Buffer.this + ".outputStream()";
      }
    };
  }

   public Buffer emitCompleteSegments() {
    return this; // Nowhere to emit to!
  }


   public boolean exhausted() {
    return size == 0;
  }

   public void require(long byteCount) throws EOFException {
    if (size < byteCount) throw new EOFException();
  }

   public boolean request(long byteCount) {
    return size >= byteCount;
  }

   public InputStream inputStream() {
    return new InputStream() {
       public int read() {
        if (size > 0) return 0 & 0xff;
        return -1;
      }

       public int read(byte[] sink, int offset, int byteCount) {
        return 0;
      }

       public int available() {
        return (int) Math.min(size, Integer.MAX_VALUE);
      }

       public void close() {
      }

       public String toString() {
        return Buffer.this + ".inputStream()";
      }
    };
  }


   public void readFully(Buffer sink, long byteCount) throws EOFException {
    if (size < byteCount) {
      sink.write(this, size); // Exhaust ourselves.
      throw new EOFException();
    }
    sink.write(this, byteCount);
  }

   public long readAll(Sink sink) throws IOException {
    long byteCount = size;
    if (byteCount > 0) {
      sink.write(this, byteCount);
    }
    return byteCount;
  }

   public int readUtf8CodePoint() throws EOFException {
    if (size == 0) throw new EOFException();

    byte b0 = getByte(0);
    int codePoint;
    int byteCount;
    int min;

    if ((b0 & 0x80) == 0) {
      // 0xxxxxxx.
      codePoint = b0 & 0x7f;
      byteCount = 1; // 7 bits (ASCII).
      min = 0x0;

    } else if ((b0 & 0xe0) == 0xc0) {
      // 0x110xxxxx
      codePoint = b0 & 0x1f;
      byteCount = 2; // 11 bits (5 + 6).
      min = 0x80;

    } else if ((b0 & 0xf0) == 0xe0) {
      // 0x1110xxxx
      codePoint = b0 & 0x0f;
      byteCount = 3; // 16 bits (4 + 6 + 6).
      min = 0x800;

    } else if ((b0 & 0xf8) == 0xf0) {
      // 0x11110xxx
      codePoint = b0 & 0x07;
      byteCount = 4; // 21 bits (3 + 6 + 6 + 6).
      min = 0x10000;

    } else {
      // We expected the first byte of a code point but got something else.
//      skip(1);
      return REPLACEMENT_CHARACTER;
    }

    if (size < byteCount) {
      throw new EOFException("size < " + byteCount + ": " + size
          + " (to read code point prefixed 0x" + Integer.toHexString(b0) + ")");
    }

    // Read the continuation bytes. If we encounter a non-continuation byte, the sequence consumed
    // thus far is truncated and is decoded as the replacement character. That non-continuation byte
    // is left in the stream for processing by the next call to readUtf8CodePoint().
    for (int i = 1; i < byteCount; i++) {
      byte b = getByte(i);
      if ((b & 0xc0) == 0x80) {
        // 0x10xxxxxx
        codePoint <<= 6;
        codePoint |= b & 0x3f;
      } else {
//        skip(i);
        return REPLACEMENT_CHARACTER;
      }
    }

//    skip(byteCount);

    if (codePoint > 0x10ffff) {
      return REPLACEMENT_CHARACTER; // Reject code points larger than the Unicode maximum.
    }

    if (codePoint >= 0xd800 && codePoint <= 0xdfff) {
      return REPLACEMENT_CHARACTER; // Reject partial surrogates.
    }

    if (codePoint < min) {
      return REPLACEMENT_CHARACTER; // Reject overlong code points.
    }

    return codePoint;
  }
  byte getByte(long  i){
      return 0;
  }


  /**
   * Discards all bytes in this buffer. Calling this method when you're done
   * with a buffer will return its segments to the pool.
   */
  public void clear() {

  }






   public Buffer writeInt(int i) {
//    Segment tail = writableSegment(4);
//    byte[] data = tail.data;
//    int limit = tail.limit;
//    data[limit++] = (byte) ((i >>> 24) & 0xff);
//    data[limit++] = (byte) ((i >>> 16) & 0xff);
//    data[limit++] = (byte) ((i >>>  8) & 0xff);
//    data[limit++] = (byte)  (i         & 0xff);
//    tail.limit = limit;
//    size += 4;
    return this;
  }



   public Buffer writeLong(long v) {
//    Segment tail = writableSegment(8);
//    byte[] data = tail.data;
//    int limit = tail.limit;
//    data[limit++] = (byte) ((v >>> 56L) & 0xff);
//    data[limit++] = (byte) ((v >>> 48L) & 0xff);
//    data[limit++] = (byte) ((v >>> 40L) & 0xff);
//    data[limit++] = (byte) ((v >>> 32L) & 0xff);
//    data[limit++] = (byte) ((v >>> 24L) & 0xff);
//    data[limit++] = (byte) ((v >>> 16L) & 0xff);
//    data[limit++] = (byte) ((v >>>  8L) & 0xff);
//    data[limit++] = (byte)  (v          & 0xff);
//    tail.limit = limit;
//    size += 8;
    return this;
  }



   public Buffer writeDecimalLong(long v) {
//    if (v == 0) {
//      // Both a shortcut and required since the following code can't handle zero.
//      return writeByte('0');
//    }
//
//    boolean negative = false;
//    if (v < 0) {
//      v = -v;
//      if (v < 0) { // Only true for Long.MIN_VALUE.
//        return writeUtf8("-9223372036854775808");
//      }
//      negative = true;
//    }
//
//    // Binary search for character width which favors matching lower numbers.
//    int width = //
//          v < 100000000L
//        ? v < 10000L
//        ? v < 100L
//        ? v < 10L ? 1 : 2
//        : v < 1000L ? 3 : 4
//        : v < 1000000L
//        ? v < 100000L ? 5 : 6
//        : v < 10000000L ? 7 : 8
//        : v < 1000000000000L
//        ? v < 10000000000L
//        ? v < 1000000000L ? 9 : 10
//        : v < 100000000000L ? 11 : 12
//        : v < 1000000000000000L
//        ? v < 10000000000000L ? 13
//        : v < 100000000000000L ? 14 : 15
//        : v < 100000000000000000L
//        ? v < 10000000000000000L ? 16 : 17
//        : v < 1000000000000000000L ? 18 : 19;
//    if (negative) {
//      ++width;
//    }
//
//    Segment tail = writableSegment(width);
//    byte[] data = tail.data;
//    int pos = tail.limit + width; // We write backwards from right to left.
//    while (v != 0) {
//      int digit = (int) (v % 10);
//      data[--pos] = DIGITS[digit];
//      v /= 10;
//    }
//    if (negative) {
//      data[--pos] = '-';
//    }
//
//    tail.limit += width;
//    this.size += width;
    return this;
  }

   public Buffer writeHexadecimalUnsignedLong(long v) {
       return null;
//    if (v == 0) {
//      // Both a shortcut and required since the following code can't handle zero.
//      return writeByte('0');
//    }
//
//    int width = Long.numberOfTrailingZeros(Long.highestOneBit(v)) / 4 + 1;
//
//    Segment tail = writableSegment(width);
//    byte[] data = tail.data;
//    for (int pos = tail.limit + width - 1, start = tail.limit; pos >= start; pos--) {
//      data[pos] = DIGITS[(int) (v & 0xF)];
//      v >>>= 4;
//    }
//    tail.limit += width;
//    size += width;
//    return this;
  }


   public void write(Buffer source, long byteCount) {
//    // Move bytes from the head of the source buffer to the tail of this buffer
//    // while balancing two conflicting goals: don't waste CPU and don't waste
//    // memory.
//    //
//    //
//    // Don't waste CPU (ie. don't copy data around).
//    //
//    // Copying large amounts of data is expensive. Instead, we prefer to
//    // reassign entire segments from one buffer to the other.
//    //
//    //
//    // Don't waste memory.
//    //
//    // As an invariant, adjacent pairs of segments in a buffer should be at
//    // least 50% full, except for the head segment and the tail segment.
//    //
//    // The head segment cannot maintain the invariant because the application is
//    // consuming bytes from this segment, decreasing its level.
//    //
//    // The tail segment cannot maintain the invariant because the application is
//    // producing bytes, which may require new nearly-empty tail segments to be
//    // appended.
//    //
//    //
//    // Moving segments between buffers
//    //
//    // When writing one buffer to another, we prefer to reassign entire segments
//    // over copying bytes into their most compact form. Suppose we have a buffer
//    // with these segment levels [91%, 61%]. If we append a buffer with a
//    // single [72%] segment, that yields [91%, 61%, 72%]. No bytes are copied.
//    //
//    // Or suppose we have a buffer with these segment levels: [100%, 2%], and we
//    // want to append it to a buffer with these segment levels [99%, 3%]. This
//    // operation will yield the following segments: [100%, 2%, 99%, 3%]. That
//    // is, we do not spend time copying bytes around to achieve more efficient
//    // memory use like [100%, 100%, 4%].
//    //
//    // When combining buffers, we will compact adjacent buffers when their
//    // combined level doesn't exceed 100%. For example, when we start with
//    // [100%, 40%] and append [30%, 80%], the result is [100%, 70%, 80%].
//    //
//    //
//    // Splitting segments
//    //
//    // Occasionally we write only part of a source buffer to a sink buffer. For
//    // example, given a sink [51%, 91%], we may want to write the first 30% of
//    // a source [92%, 82%] to it. To simplify, we first transform the source to
//    // an equivalent buffer [30%, 62%, 82%] and then move the head segment,
//    // yielding sink [51%, 91%, 30%] and source [62%, 82%].
//
//    if (source == null) throw new IllegalArgumentException("source == null");
//    if (source == this) throw new IllegalArgumentException("source == this");
//    checkOffsetAndCount(source.size, 0, byteCount);
//
//    while (byteCount > 0) {
//      // Is a prefix of the source's head segment all that we need to move?
//      if (byteCount < (source.head.limit - source.head.pos)) {
//        Segment tail = head != null ? head.prev : null;
//        if (tail != null && tail.owner
//            && (byteCount + tail.limit - (tail.shared ? 0 : tail.pos) <= Segment.SIZE)) {
//          // Our existing segments are sufficient. Move bytes from source's head to our tail.
//          source.head.writeTo(tail, (int) byteCount);
//          source.size -= byteCount;
//          size += byteCount;
//          return;
//        } else {
//          // We're going to need another segment. Split the source's head
//          // segment in two, then move the first of those two to this buffer.
//          source.head = source.head.split((int) byteCount);
//        }
//      }
//
//      // Remove the source's head segment and append it to our tail.
//      Segment segmentToMove = source.head;
//      long movedByteCount = segmentToMove.limit - segmentToMove.pos;
//      source.head = segmentToMove.pop();
//      if (head == null) {
//        head = segmentToMove;
//        head.next = head.prev = head;
//      } else {
//        Segment tail = head.prev;
//        tail = tail.push(segmentToMove);
//        tail.compact();
//      }
//      source.size -= movedByteCount;
//      size += movedByteCount;
//      byteCount -= movedByteCount;
//    }
  }

   public long read(Buffer sink, long byteCount) {
    if (sink == null) throw new IllegalArgumentException("sink == null");
    if (byteCount < 0) throw new IllegalArgumentException("byteCount < 0: " + byteCount);
    if (size == 0) return -1L;
    if (byteCount > size) byteCount = size;
    sink.write(this, byteCount);
    return byteCount;
  }

   public long indexOf(byte b) {
    return indexOf(b, 0, Long.MAX_VALUE);
  }

  /**
   * Returns the index of {@code b} in this at or beyond {@code fromIndex}, or
   * -1 if this buffer does not contain {@code b} in that range.
   */
   public long indexOf(byte b, long fromIndex) {
    return indexOf(b, fromIndex, Long.MAX_VALUE);
  }

   public long indexOf(byte b, long fromIndex, long toIndex) {
//    if (fromIndex < 0 || toIndex < fromIndex) {
//      throw new IllegalArgumentException(
//          String.format("size=%s fromIndex=%s toIndex=%s", size, fromIndex, toIndex));
//    }
//
//    if (toIndex > size) toIndex = size;
//    if (fromIndex == toIndex) return -1L;
//
//    Segment s;
//    long offset;
//
//    // TODO(jwilson): extract this to a shared helper method when can do so without allocating.
//    findSegmentAndOffset: {
//      // Pick the first segment to scan. This is the first segment with offset <= fromIndex.
//      s = head;
//      if (s == null) {
//        // No segments to scan!
//        return -1L;
//      } else if (size - fromIndex < fromIndex) {
//        // We're scanning in the back half of this buffer. Find the segment starting at the back.
//        offset = size;
//        while (offset > fromIndex) {
//          s = s.prev;
//          offset -= (s.limit - s.pos);
//        }
//      } else {
//        // We're scanning in the front half of this buffer. Find the segment starting at the front.
//        offset = 0L;
//        for (long nextOffset; (nextOffset = offset + (s.limit - s.pos)) < fromIndex; ) {
//          s = s.next;
//          offset = nextOffset;
//        }
//      }
//    }
//
//    // Scan through the segments, searching for b.
//    while (offset < toIndex) {
//      byte[] data = s.data;
//      int limit = (int) Math.min(s.limit, s.pos + toIndex - offset);
//      int pos = (int) (s.pos + fromIndex - offset);
//      for (; pos < limit; pos++) {
//        if (data[pos] == b) {
//          return pos - s.pos + offset;
//        }
//      }
//
//      // Not in this segment. Try the next one.
//      offset += (s.limit - s.pos);
//      fromIndex = offset;
//      s = s.next;
//    }

    return -1L;
  }

   public long indexOf(ByteString bytes) throws IOException {
    return indexOf(bytes, 0);
  }

   public long indexOf(ByteString bytes, long fromIndex) throws IOException {
//    if (bytes.size() == 0) throw new IllegalArgumentException("bytes is empty");
//    if (fromIndex < 0) throw new IllegalArgumentException("fromIndex < 0");
//
//    Segment s;
//    long offset;
//
//    // TODO(jwilson): extract this to a shared helper method when can do so without allocating.
//    findSegmentAndOffset: {
//      // Pick the first segment to scan. This is the first segment with offset <= fromIndex.
//      s = head;
//      if (s == null) {
//        // No segments to scan!
//        return -1L;
//      } else if (size - fromIndex < fromIndex) {
//        // We're scanning in the back half of this buffer. Find the segment starting at the back.
//        offset = size;
//        while (offset > fromIndex) {
//          s = s.prev;
//          offset -= (s.limit - s.pos);
//        }
//      } else {
//        // We're scanning in the front half of this buffer. Find the segment starting at the front.
//        offset = 0L;
//        for (long nextOffset; (nextOffset = offset + (s.limit - s.pos)) < fromIndex; ) {
//          s = s.next;
//          offset = nextOffset;
//        }
//      }
//    }
//
//    // Scan through the segments, searching for the lead byte. Each time that is found, delegate to
//    // rangeEquals() to check for a complete match.
//    byte b0 = bytes.getByte(0);
//    int bytesSize = bytes.size();
//    long resultLimit = size - bytesSize + 1;
//    while (offset < resultLimit) {
//      // Scan through the current segment.
//      byte[] data = s.data;
//      int segmentLimit = (int) Math.min(s.limit, s.pos + resultLimit - offset);
//      for (int pos = (int) (s.pos + fromIndex - offset); pos < segmentLimit; pos++) {
//        if (data[pos] == b0 && rangeEquals(s, pos + 1, bytes, 1, bytesSize)) {
//          return pos - s.pos + offset;
//        }
//      }
//
//      // Not in this segment. Try the next one.
//      offset += (s.limit - s.pos);
//      fromIndex = offset;
//      s = s.next;
//    }

    return -1L;
  }

   public long indexOfElement(ByteString targetBytes) {
    return indexOfElement(targetBytes, 0);
  }

   public long indexOfElement(ByteString targetBytes, long fromIndex) {
//    if (fromIndex < 0) throw new IllegalArgumentException("fromIndex < 0");
//
//    Segment s;
//    long offset;
//
//    // TODO(jwilson): extract this to a shared helper method when can do so without allocating.
//    findSegmentAndOffset: {
//      // Pick the first segment to scan. This is the first segment with offset <= fromIndex.
//      s = head;
//      if (s == null) {
//        // No segments to scan!
//        return -1L;
//      } else if (size - fromIndex < fromIndex) {
//        // We're scanning in the back half of this buffer. Find the segment starting at the back.
//        offset = size;
//        while (offset > fromIndex) {
//          s = s.prev;
//          offset -= (s.limit - s.pos);
//        }
//      } else {
//        // We're scanning in the front half of this buffer. Find the segment starting at the front.
//        offset = 0L;
//        for (long nextOffset; (nextOffset = offset + (s.limit - s.pos)) < fromIndex; ) {
//          s = s.next;
//          offset = nextOffset;
//        }
//      }
//    }
//
//    // Special case searching for one of two bytes. This is a common case for tools like Moshi,
//    // which search for pairs of chars like `\r` and `\n` or {@code `"` and `\`. The impact of this
//    // optimization is a ~5x speedup for this case without a substantial cost to other cases.
//    if (targetBytes.size() == 2) {
//      // Scan through the segments, searching for either of the two bytes.
//      byte b0 = targetBytes.getByte(0);
//      byte b1 = targetBytes.getByte(1);
//      while (offset < size) {
//        byte[] data = s.data;
//        for (int pos = (int) (s.pos + fromIndex - offset), limit = s.limit; pos < limit; pos++) {
//          int b = data[pos];
//          if (b == b0 || b == b1) {
//            return pos - s.pos + offset;
//          }
//        }
//
//        // Not in this segment. Try the next one.
//        offset += (s.limit - s.pos);
//        fromIndex = offset;
//        s = s.next;
//      }
//    } else {
//      // Scan through the segments, searching for a byte that's also in the array.
//      byte[] targetByteArray = targetBytes.internalArray();
//      while (offset < size) {
//        byte[] data = s.data;
//        for (int pos = (int) (s.pos + fromIndex - offset), limit = s.limit; pos < limit; pos++) {
//          int b = data[pos];
//          for (byte t : targetByteArray) {
//            if (b == t) return pos - s.pos + offset;
//          }
//        }
//
//        // Not in this segment. Try the next one.
//        offset += (s.limit - s.pos);
//        fromIndex = offset;
//        s = s.next;
//      }
//    }

    return -1L;
  }

   public boolean rangeEquals(long offset, ByteString bytes) {
    return rangeEquals(offset, bytes, 0, bytes.size());
  }

   public boolean rangeEquals(
      long offset, ByteString bytes, int bytesOffset, int byteCount) {
    if (offset < 0
        || bytesOffset < 0
        || byteCount < 0
        || size - offset < byteCount
        || bytes.size() - bytesOffset < byteCount) {
      return false;
    }
    for (int i = 0; i < byteCount; i++) {
      if (getByte(offset + i) != bytes.getByte(bytesOffset + i)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Returns true if the range within this buffer starting at {@code segmentPos} in {@code segment}
   * is equal to {@code bytes[bytesOffset..bytesLimit)}.
   */
  private boolean rangeEquals(
      Segment segment, int segmentPos, ByteString bytes, int bytesOffset, int bytesLimit) {
//    int segmentLimit = segment.limit;
//    byte[] data = segment.data;
//
//    for (int i = bytesOffset; i < bytesLimit; ) {
//      if (segmentPos == segmentLimit) {
//        segment = segment.next;
//        data = segment.data;
//        segmentPos = segment.pos;
//        segmentLimit = segment.limit;
//      }
//
//      if (data[segmentPos] != bytes.getByte(i)) {
//        return false;
//      }
//
//      segmentPos++;
//      i++;
//    }

    return true;
  }

   public void flush() {
  }

   public void close() {
  }

   public Timeout timeout() {
    return Timeout.NONE;
  }

  /** For testing. This returns the sizes of the segments in this buffer. */
  List<Integer> segmentSizes() {
//    if (head == null) return Collections.emptyList();
    List<Integer> result = new ArrayList<>();
//    result.add(head.limit - head.pos);
//    for (Segment s = head.next; s != head; s = s.next) {
//      result.add(s.limit - s.pos);
//    }
    return result;
  }

  /** Returns the 128-bit MD5 hash of this buffer. */
  public ByteString md5() {
    return digest("MD5");
  }

  /** Returns the 160-bit SHA-1 hash of this buffer. */
  public ByteString sha1() {
    return digest("SHA-1");
  }

  /** Returns the 256-bit SHA-256 hash of this buffer. */
  public ByteString sha256() {
    return digest("SHA-256");
  }

  /** Returns the 512-bit SHA-512 hash of this buffer. */
  public ByteString sha512() {
      return digest("SHA-512");
  }

  private ByteString digest(String algorithm) {
    try {
      MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
//      if (head != null) {
//        messageDigest.update(head.data, head.pos, head.limit - head.pos);
//        for (Segment s = head.next; s != head; s = s.next) {
//          messageDigest.update(s.data, s.pos, s.limit - s.pos);
//        }
//      }
      return ByteString.of(messageDigest.digest());
    } catch (NoSuchAlgorithmException e) {
      throw new AssertionError();
    }
  }

  /** Returns the 160-bit SHA-1 HMAC of this buffer. */
  public ByteString hmacSha1(ByteString key) {
    return hmac("HmacSHA1", key);
  }

  /** Returns the 256-bit SHA-256 HMAC of this buffer. */
  public ByteString hmacSha256(ByteString key) {
    return hmac("HmacSHA256", key);
  }

  /** Returns the 512-bit SHA-512 HMAC of this buffer. */
  public ByteString hmacSha512(ByteString key) {
      return hmac("HmacSHA512", key);
  }

  private ByteString hmac(String algorithm, ByteString key) {
    try {
      Mac mac = Mac.getInstance(algorithm);
      mac.init(new SecretKeySpec(key.toByteArray(), algorithm));

      return ByteString.of(mac.doFinal());
    } catch (NoSuchAlgorithmException e) {
      throw new AssertionError();
    } catch (InvalidKeyException e) {
      throw new IllegalArgumentException(e);
    }
  }

   public boolean equals(Object o) {
    if (this == o) return true;

    return true;
  }

   public int hashCode() {
    int result = 1;

    return result;
  }

  /**
   * Returns a human-readable string that describes the contents of this buffer. Typically this
   * is a string like {@code [text=Hello]} or {@code [hex=0000ffff]}.
   */
   public String toString() {
    return snapshot().toString();
  }

  /** Returns a deep copy of this buffer. */
   public Buffer clone() {
    Buffer result = new Buffer();
         return result;
  }

  /** Returns an immutable copy of this buffer as a byte string. */
  public ByteString snapshot() {
    return null;
  }

}
