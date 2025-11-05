/**
 * $Id$
 * 
 * Copyright (c) 2011-17 Stephane GALLAND <stephane.galland@utbm.fr>.
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
package framework.environment;

import io.sarl.lang.core.annotation.SarlElementType;
import io.sarl.lang.core.annotation.SarlSpecification;
import io.sarl.lang.core.annotation.SyntheticMember;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.XbaseGenerated;

/**
 * Abstract implementation of a field-of-view.
 * This implementation provides the default filtering behavior: remove the perceiver's body
 * from the perception.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
@SarlSpecification("0.15")
@SarlElementType(10)
@XbaseGenerated
@SuppressWarnings("all")
public abstract class AbstractFrustum implements Frustum {
  /**
   * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
   * @version $Name$ $Revision$ $Date$
   */
  @SarlSpecification("0.15")
  @SarlElementType(10)
  @XbaseGenerated
  private static class PerceiverBodyFilter<D extends SituatedObject> implements Iterator<D> {
    private final AbstractFrustum enclosing;

    private final Iterator<D> original;

    private D next;

    public PerceiverBodyFilter(final AbstractFrustum enclosing, final Iterator<D> original) {
      this.enclosing = enclosing;
      this.original = original;
      this.searchNext();
    }

    private void searchNext() {
      this.next = null;
      while (((this.next == null) && this.original.hasNext())) {
        {
          D candidate = this.original.next();
          UUID _iD = candidate.getID();
          boolean _notEquals = (!Objects.equals(_iD, this.enclosing.owner));
          if (_notEquals) {
            this.next = candidate;
          }
        }
      }
    }

    @Override
    public boolean hasNext() {
      return (this.next != null);
    }

    @Override
    public D next() {
      D n = this.next;
      if ((n == null)) {
        throw new NoSuchElementException();
      }
      this.searchNext();
      return n;
    }

    @Override
    @Pure
    @SyntheticMember
    public boolean equals(final Object obj) {
      return super.equals(obj);
    }

    @Override
    @Pure
    @SyntheticMember
    public int hashCode() {
      int result = super.hashCode();
      return result;
    }
  }

  private final UUID owner;

  /**
   * @param owner the identifier of the owner of this frustum.
   */
  public AbstractFrustum(final UUID owner) {
    this.owner = owner;
  }

  @Override
  @Pure
  public UUID getOwner() {
    return this.owner;
  }

  @Override
  public <D extends SituatedObject> Iterator<D> filter(final Iterator<D> iterator) {
    return new AbstractFrustum.PerceiverBodyFilter<D>(this, iterator);
  }

  @Override
  @Pure
  @SyntheticMember
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    AbstractFrustum other = (AbstractFrustum) obj;
    if (!Objects.equals(this.owner, other.owner))
      return false;
    return super.equals(obj);
  }

  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Objects.hashCode(this.owner);
    return result;
  }

  @Override
  @Pure
  @SyntheticMember
  public AbstractFrustum clone() {
    try {
      return (AbstractFrustum) super.clone();
    } catch (Throwable exception) {
      throw new Error(exception);
    }
  }

  @SyntheticMember
  private static final long serialVersionUID = 4667860150L;
}
