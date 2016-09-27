package test.ibatis;

import java.util.List;

import org.apache.ibatis.annotations.Select;

public interface AddressMapper {

	@Select("select * from address")
	List<Address> list();
}
