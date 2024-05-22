package com.chunjae.allforclass.service;

import com.chunjae.allforclass.dto.LecDTO;

public interface PurchaseService {

    LecDTO detailLec(int lid);
    boolean isReserved(int sessionId, int lid);

}
