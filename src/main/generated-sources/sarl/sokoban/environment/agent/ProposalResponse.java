/**
 * $Id$
 * 
 * Copyright (c) 2015-17 Stephane GALLAND.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * This program is free software; you can redistribute it and/or modify
 */
package sokoban.environment.agent;

import framework.math.Point2i;
import io.sarl.lang.core.Event;
import io.sarl.lang.core.annotation.SarlElementType;
import io.sarl.lang.core.annotation.SarlSpecification;
import io.sarl.lang.core.annotation.SyntheticMember;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

/**
 * Event to respond to a path proposal during negotiation.
 * 
 * Agents emit this event during the NEGOTIATION phase to accept or reject proposals.
 * This enables decentralized coordination and optimal box assignment.
 */
@SarlSpecification("0.15")
@SarlElementType(15)
@XbaseGenerated
@SuppressWarnings("all")
public class ProposalResponse extends Event {
  public final UUID proposalAgentId;

  public final Point2i boxPosition;

  public final boolean accepted;

  public final UUID responderId;

  public final double alternativeScore;

  public ProposalResponse(final UUID proposalAgentId, final Point2i boxPosition, final boolean accepted, final UUID responderId, final double alternativeScore) {
    this.proposalAgentId = proposalAgentId;
    this.boxPosition = boxPosition;
    this.accepted = accepted;
    this.responderId = responderId;
    this.alternativeScore = alternativeScore;
  }

  @Override
  @Pure
  @SyntheticMember
  public boolean equals(final Object obj) {
    throw new Error("Unresolved compilation problems:"
      + "\nInvalid supertype. Expecting a class"
      + "\nInvalid supertype. Expecting a class");
  }

  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    throw new Error("Unresolved compilation problems:"
      + "\nInvalid supertype. Expecting a class");
  }

  /**
   * Returns a String representation of the ProposalResponse event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    throw new Error("Unresolved compilation problems:"
      + "\nInvalid supertype. Expecting a class");
  }

  @SyntheticMember
  private static final long serialVersionUID = -855365496L;
}
