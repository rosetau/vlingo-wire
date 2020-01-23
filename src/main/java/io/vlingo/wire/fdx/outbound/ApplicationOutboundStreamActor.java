// Copyright © 2012-2020 VLINGO LABS. All rights reserved.
//
// This Source Code Form is subject to the terms of the
// Mozilla Public License, v. 2.0. If a copy of the MPL
// was not distributed with this file, You can obtain
// one at https://mozilla.org/MPL/2.0/.

package io.vlingo.wire.fdx.outbound;

import io.vlingo.actors.Actor;
import io.vlingo.common.pool.ResourcePool;
import io.vlingo.wire.message.ConsumerByteBuffer;
import io.vlingo.wire.message.RawMessage;
import io.vlingo.wire.node.Id;

public class ApplicationOutboundStreamActor extends Actor
  implements ApplicationOutboundStream {

  private final Outbound outbound;
  
  public ApplicationOutboundStreamActor(
          final ManagedOutboundChannelProvider provider,
          final ResourcePool<ConsumerByteBuffer, String> byteBufferPool) {
    
    this.outbound = new Outbound(provider, byteBufferPool);
  }

  //===================================
  // ClusterApplicationOutboundStream
  //===================================

  @Override
  public void broadcast(final RawMessage message) {
    outbound.broadcast(message);
  }

  @Override
  public void sendTo(final RawMessage message, final Id targetId) {
    outbound.sendTo(message, targetId);
  }

  //===================================
  // Stoppable
  //===================================
  
  public void stop() {
    outbound.close();
    
    super.stop();
  }
}
