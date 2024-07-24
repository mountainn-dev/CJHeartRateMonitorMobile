package com.san.heartratemonitormobile.data.exception

import java.io.IOException

object ServiceException {
    /**
     * class ResultException
     *
     * 서비스 조회 결과 관련 Exception
     */
    class NoResultException(override val message: String?) : IOException(message)

    /**
     * class SignUpException
     *
     * 회원가입 관련 Exception
     */
    class SignUpException(override val message: String?) : IOException(message)

    /**
     * class LoginException
     *
     * 로그인 관련 Exception
     */
    class LoginException(override val message: String?) : IOException(message)

    /**
     * class ServerException
     *
     * 서비스 서버 관련 Exception
     */
    class ServerException(override val message: String?) : IOException(message)
}