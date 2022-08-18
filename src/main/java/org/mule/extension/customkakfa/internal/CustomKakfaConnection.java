package org.mule.extension.customkakfa.internal;


/**
 * This class represents an extension connection just as example (there is no real connection with anything here c:).
 */
public final class CustomKakfaConnection {

  private final CustomKakfaConfiguration config;

  public CustomKakfaConnection(CustomKakfaConfiguration config) {
    this.config = config;
  }

 
  public CustomKakfaConfiguration getConfig() {
    return config;
  }


  public void invalidate() {
    // do something to invalidate this connection!
  }
}
