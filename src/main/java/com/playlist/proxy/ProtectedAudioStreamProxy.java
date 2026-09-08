package com.playlist.proxy;

import com.playlist.core.AccessDeniedException;
import com.playlist.core.Subscription;
import com.playlist.core.Track;
import java.util.function.Supplier;

public class ProtectedAudioStreamProxy implements AudioStream {

  private final Subscription plan;
  private final Track track;
  private final Supplier<AudioStream> loader;
  private AudioStream realStream;
  private byte[] cachedBytes;


  public ProtectedAudioStreamProxy(Track track, Subscription plan, Supplier<AudioStream> loader) {
    throw new UnsupportedOperationException();

    this.realStream
    this.loader = loader
    this.cachedBytes = cachedBytes
    this.
  }

  public ProtectedAudioStreamProxy(Track track, Subscription plan) {
    throw new UnsupportedOperationException();
  }


  public boolean isLoaded() {
    throw new UnsupportedOperationException();
  }

  @Override
  public String getTrackId() {
    throw new UnsupportedOperationException();
  }


  @Override
  public byte[] readBytes() {
    throw new UnsupportedOperationException();
  }
}

