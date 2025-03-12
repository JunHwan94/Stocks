package com.zzunapps.stocks.data

import com.google.gson.annotations.SerializedName

data class OverseasPriceResponse(
    @SerializedName("msg1") val msg: String, // 응답 메세지
    @SerializedName("msg_cd") val msgCd: String, // 응답 코드
    @SerializedName("rt_cd") val rtCd: String, // 성공 여부 0: 성공, 0이외: 실패
    @SerializedName("output") val priceDetail: PriceDetail, // 현재 주가 상세 정보
)

data class PriceDetail(
    @SerializedName("rsym") val rsym: String, // 실시간조회종목코드
    @SerializedName("pvol") val pvol: String, // 전일거래량
    @SerializedName("open") val open: String, // 시가
    @SerializedName("high") val high: String, // 고가
    @SerializedName("low") val low: String, // 저가
    @SerializedName("last") val last: String, // 현재가
    @SerializedName("base") val base: String, // 전일종가
    @SerializedName("tomv") val tomv: String, // 시가총액
    @SerializedName("pamt") val pamt: String, // 전일거래대금
    @SerializedName("uplp") val uplp: String, // 상한가
    @SerializedName("dnlp") val dnlp: String, // 하한가
    @SerializedName("h52p") val h52p: String, // 52주 최고가
    @SerializedName("h52d") val h52d: String, // 52주 최고일자
    @SerializedName("l52p") val l52p: String, // 52주 최저가
    @SerializedName("l52d") val l52d: String, // 52주 최저일자
    @SerializedName("perx") val per: String, // PER
    @SerializedName("pbrx") val pbr: String, // PBR
    @SerializedName("epsx") val eps: String, // EPS
    @SerializedName("bpsx") val bps: String, // BPS
    @SerializedName("sharx") val shar: String, // 상장주수
    @SerializedName("mcap") val mcap: String, // 자본금
    @SerializedName("curr") val currency: String, // 통화
    @SerializedName("zdiv") val zdiv: String, // 소수점자리수
    @SerializedName("vnit") val vnit: String, // 매매단위
    @SerializedName("t_xprc") val t_xprc: String, // 원환산당일가격
    @SerializedName("t_xdif") val t_xdif: String, // 원환상당일대비
    @SerializedName("t_xrat") val t_xrat: String, // 원환산당일등락
    @SerializedName("p_xprc") val p_xprc: String, // 원환산전일가격
    @SerializedName("p_xdif") val p_xdif: String, // 원환산전일대비
    @SerializedName("p_xrat") val p_xrat: String, // 원환산전일등락
    @SerializedName("t_rate") val t_rate: String, // 당일환율
    @SerializedName("p_rate") val p_rate: String, // 전일환율
    @SerializedName("t_xsgn") val t_xsgn: String, // 원환산당일기호
    @SerializedName("p_xsng") val p_xsng: String, // 원환산전일기호
    @SerializedName("e_ordyn") val e_ordyn: String, // 거래가능여부
    @SerializedName("e_hogau") val e_hogau: String, // 호가단위
    @SerializedName("e_icod") val e_icod: String, // 업종(섹터)
    @SerializedName("e_parp") val e_parp: String, // 원환산액면가전일기호
    @SerializedName("tvol") val tvol: String, // 거래량
    @SerializedName("tamt") val tamt: String, // 거래대금
    @SerializedName("etyp_nm") val etyp_nm: String, // ETP 분류명
)