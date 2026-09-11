package com.playlist.proxy;

import com.playlist.core.AccessDeniedException;
import com.playlist.core.Subscription;
import com.playlist.core.Track;
import java.util.function.Supplier;


public class ProtectedAudioStreamProxy implements AudioStream {

  private final Track track;
  private final Subscription plan;
  private final Supplier<AudioStream> loader;
  private AudioStream stream;
  private byte[] cache;


  public ProtectedAudioStreamProxy(Track track, Subscription plan, Supplier<AudioStream> loader) {
    if (track == null || plan == null || loader == null) {
      throw new IllegalArgumentException("");
    }

    this.track = track;
    this.plan = plan;
    this.loader = loader;

  }


  public ProtectedAudioStreamProxy(Track track, Subscription plan) {
    this(track, plan, () -> new RemoteAudioStream(track)); //LL

  }


  public boolean isLoaded() {
    if (stream == null) {
      return false;
    }
    return true;

  }

  @Override
  public String getTrackId() {
    return track.id();

  }

  @Override
  public byte[] readBytes() {
    if (track.premium() && plan == Subscription.FREE) {
      throw new AccessDeniedException("");
    }

    if (stream == null) {
      stream = loader.get();
    }

    if (cache == null) {
      cache = stream.readBytes();
    }

    return cache.clone();
  }
}
