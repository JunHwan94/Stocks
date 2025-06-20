package com.zzunapps.stocks.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OverseasPriceResponse(
    @SerialName("msg1") val msg: String, // 응답 메세지
    @SerialName("msg_cd") val msgCd: String, // 응답 코드
    @SerialName("rt_cd") val rtCd: String, // 성공 여부 0: 성공, 0이외: 실패
    @SerialName("output") val priceDetail: PriceDetail, // 현재 주가 상세 정보
)

@Serializable
data class PriceDetail(
    @SerialName("rsym") val rsym: String, // 실시간조회종목코드
    @SerialName("pvol") val pvol: String, // 전일거래량
    @SerialName("open") val open: String, // 시가
    @SerialName("high") val high: String, // 고가
    @SerialName("low") val low: String, // 저가
    @SerialName("last") val last: String, // 현재가
    @SerialName("base") val base: String, // 전일종가
    @SerialName("tomv") val tomv: String, // 시가총액
    @SerialName("pamt") val pamt: String, // 전일거래대금
    @SerialName("uplp") val uplp: String, // 상한가
    @SerialName("dnlp") val dnlp: String, // 하한가
    @SerialName("h52p") val h52p: String, // 52주 최고가
    @SerialName("h52d") val h52d: String, // 52주 최고일자
    @SerialName("l52p") val l52p: String, // 52주 최저가
    @SerialName("l52d") val l52d: String, // 52주 최저일자
    @SerialName("perx") val per: String, // PER
    @SerialName("pbrx") val pbr: String, // PBR
    @SerialName("epsx") val eps: String, // EPS
    @SerialName("bpsx") val bps: String, // BPS
    @SerialName("shar") val shar: String, // 상장주수
    @SerialName("mcap") val mcap: String, // 자본금
    @SerialName("curr") val currency: String, // 통화
    @SerialName("zdiv") val zdiv: String, // 소수점자리수
    @SerialName("vnit") val vnit: String, // 매매단위
    @SerialName("t_xprc") val t_xprc: String, // 원환산당일가격
    @SerialName("t_xdif") val t_xdif: String, // 원환상당일대비
    @SerialName("t_xrat") val t_xrat: String, // 원환산당일등락
    @SerialName("p_xprc") val p_xprc: String, // 원환산전일가격
    @SerialName("p_xdif") val p_xdif: String, // 원환산전일대비
    @SerialName("p_xrat") val p_xrat: String, // 원환산전일등락
    @SerialName("t_rate") val t_rate: String, // 당일환율
    @SerialName("p_rate") val p_rate: String, // 전일환율
    @SerialName("t_xsgn") val t_xsgn: String, // 원환산당일기호
    @SerialName("p_xsng") val p_xsng: String, // 원환산전일기호
    @SerialName("e_ordyn") val e_ordyn: String, // 거래가능여부
    @SerialName("e_hogau") val e_hogau: String, // 호가단위
    @SerialName("e_icod") val e_icod: String, // 업종(섹터)
    @SerialName("e_parp") val e_parp: String, // 원환산액면가전일기호
    @SerialName("tvol") val tvol: String, // 거래량
    @SerialName("tamt") val tamt: String, // 거래대금
    @SerialName("etyp_nm") val etyp_nm: String, // ETP 분류명
)