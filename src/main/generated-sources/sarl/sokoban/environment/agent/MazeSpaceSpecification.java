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

import io.sarl.api.core.spaces.OpenEventSpace;
import io.sarl.lang.core.EventListener;
import io.sarl.lang.core.Space;
import io.sarl.lang.core.SpaceID;
import io.sarl.lang.core.SpaceSpecification;
import io.sarl.lang.core.annotation.SarlElementType;
import io.sarl.lang.core.annotation.SarlSpecification;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;

/**
 * Space that is representing the Jaak environment.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SarlSpecification("0.15")
@SarlElementType(10)
@XbaseGenerated
@SuppressWarnings("all")
public class MazeSpaceSpecification implements SpaceSpecification<MazeSpace> {
  public MazeSpaceSpecification() {
  }

  public MazeSpace create(final SpaceID id, final Object... params) {
    if ((((params.length >= 1) && (params[0] instanceof OpenEventSpace)) && (params[1] instanceof EventListener))) {
      Object _get = params[0];
      Object _get_1 = params[1];
      return this.createSpace(id, ((OpenEventSpace) _get), ((EventListener) _get_1));
    }
    throw new IllegalArgumentException("No event listener");
  }

  /**
   * Creates a {@link Space} that respects this specification.
   * 
   * @param spaceId - the {@link SpaceID} for the newly created {@link Space}
   * @param commmunicationSpace - the communication space.
   * @return an instance of {@link Space}
   */
  @Pure
  public MazeSpace createSpace(final SpaceID spaceId, final OpenEventSpace communicationSpace, final EventListener creator) {
    MazeSpace space = new DefaultMazeSpaceImpl(spaceId, communicationSpace, creator);
    return space;
  }
}
