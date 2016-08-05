package jarvis.hashcode;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * Created by C5023792 on 8/5/2016.
 */
public class Address implements Serializable {
    private   Integer   id;
    private   String   address;
    private   String   telephone;

    public   Address(String   address,   String   telephone)   {
        super();
        this.address   =   address;
        this.telephone   =   telephone;
    }

    public   Address()   {}

    public   String   getAddress()   {
        return   address;
    }
    public   void   setAddress(String   address)   {
        this.address   =   address;
    }

    public   Integer   getId()   {
        return   id;
    }
    public   void   setId(Integer   id)   {
        this.id   =   id;
    }

    public   String   getTelephone()   {
        return   telephone;
    }
    public   void   setTelephone(String   telephone)   {
        this.telephone   =   telephone;
    }

    public   boolean   equals(Object   object)   {
        if   (!(object   instanceof   Address))   {
            return   false;
        }
        Address   anotherAddress   =   (Address)object;
        return   new EqualsBuilder().appendSuper(super.equals(object))
                .append(this.getAddress(),   anotherAddress.getAddress())
                .append(this.getTelephone(),   anotherAddress.getTelephone())
                .isEquals();
    }

    public   int   hashCode()   {
        return   new HashCodeBuilder().appendSuper(super.hashCode())
                .append(this.getAddress())
                .append(this.getTelephone())
                .hashCode();
    }
}
