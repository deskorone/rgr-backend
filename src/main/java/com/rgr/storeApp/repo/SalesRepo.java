package com.rgr.storeApp.repo;

import com.rgr.storeApp.models.profile.Sales;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesRepo extends JpaRepository<Sales, Long> {

    @Query(nativeQuery = true, value = "select * from sales s inner join sell_history as sh on sh.id = s.history_id inner join store as st on sh.store_id = st.id inner join users as bu on bu.id = s.buyer_id inner join users as u on st.id = u.store_id inner join user_profile as bup on bup.id = bu.user_profile_id inner join product as p on p.id = s.product_id inner join product_info as pi on pi.id = p.product_info_id where u.email = :email")
    Page<Sales> getSalesOnEmail(@Param("email") String email, Pageable pageable);

}
