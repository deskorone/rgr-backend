package com.rgr.storeApp.repo;

import com.rgr.storeApp.models.delivery.AwaitingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AwaitingListRepo extends JpaRepository<AwaitingList, Long> {

    //@Query(value = "select * from awaitings a inner join delivery as d on d.awaitings_id = a.id inner join buys as b on b.id = d.buy_id inner join buy_product as bp on bp.buy_id = b.id inner join product as p on p.id = bp.product_id inner join user_profile as up on up.id = a.user_profile_id inner join users as u on up.id = u.user_profile_id inner join product_info as pi on p.product_info_id = pi.id where u.email = :email", nativeQuery = true)
    AwaitingList findAllByUserProfile_User_Email(String email);

}
