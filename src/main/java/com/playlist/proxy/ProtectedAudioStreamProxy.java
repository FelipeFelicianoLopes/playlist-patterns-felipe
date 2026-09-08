package com.playlist.proxy;

import com.playlist.core.AccessDeniedException;
import com.playlist.core.Subscription;
import com.playlist.core.Track;
import java.util.function.Supplier;

public class ProtectedAudioStreamProxy implements AudioStream {

  private final Subscription plan;
  private final Supplier<AudioStream> loader;

  private AudioStream realStream;
  private byte[] cachedBytes;
  private  final Track track;

  public ProtectedAudioStreamProxy(Track track, Subscription plan, Supplier<AudioStream> loader) {


    if (track == null || plan == null || loader == null) {
      throw new IllegalArgumentException();
    }

    this.track = track;
    this.plan = plan;
    this.loader = loader;
  }


  public ProtectedAudioStreamProxy(Track track, Subscription plan) {
    if (track == null || plan == null){throw new IllegalArgumentException();}

    this.track = track;
    this.plan = plan;
    this.loader = () -> new RemoteAudioStream(track);
  }


  public boolean isLoaded() {
    return realStream != null;
  }

  @Override
  public String getTrackId() {
    return track.id();
  }


  @Override
  public byte[] readBytes() {
    throw new UnsupportedOperationException();
  }
}

