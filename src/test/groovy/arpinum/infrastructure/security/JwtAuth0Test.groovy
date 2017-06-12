package arpinum.infrastructure.security

import io.vavr.collection.HashMap
import spock.lang.Specification

class JwtAuth0Test extends Specification {

    def key = '5RPD9z_uF4Nrg1qAlJU6D1TdpF-Z6N18iHJdiOICTsAROz40CasyCbXP2x5EIKAlFzDPh4NDHEPZxewdNSouTeuWstK_igwQWiH2ONPonpoR8RDcP3pQFJKHrx4lFIZ56BybC_V-35ConGy-SEa3Ck-o-7hgsnF3FDIvVEEv57EKQrKbdZi4OEsCpP2lN22zoV4NPylJ0b1Fne9ti4Vzj1jHL0LPGEPfZQGwpXevriyAzBUMOUwKhDr2yGVm4Oz2u5laMvTXtyZlhv14lKEiS7JWzrPxOSrxtoxQlnR807h7QIdPkcEbg_9J3yBu_vxUrSpdsGG6KsIrkATlVerx1w'

    def jwt = new JwtAuth0([
            issuer: { 'alexandria' },
            secret: { key }
    ] as JwtConfiguration)

    def "can sign a payload"() {
        when:
        def token = jwt.build('id', HashMap.of('firstName', 'jb'))

        then:
        token == 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJpZCIsImZpcnN0TmFtZSI6ImpiIiwiaXNzIjoiYWxleGFuZHJpYSJ9.bypy0lbkCZD_alCuy6kAviyKpAyeXn2OXztFNaoevzw'
    }

    def "can extract sub"() {
        when:
        def sub = jwt.verify('eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJpZCIsImZpcnN0TmFtZSI6ImpiIiwiaXNzIjoiYWxleGFuZHJpYSJ9.bypy0lbkCZD_alCuy6kAviyKpAyeXn2OXztFNaoevzw')

        then:
        sub == 'id'
    }
}
