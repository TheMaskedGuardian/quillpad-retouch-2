package org.qosp.notes.network

import okhttp3.OkHttpClient
import java.security.cert.X509Certificate
import javax.net.ssl.*

/**
 * Simple SSL Trust Manager that automatically accepts all certificates.
 * This is useful for development environments with self-signed certificates.
 */
object SimpleSSLTrustManager {
    
    /**
     * Creates an OkHttpClient that automatically accepts all SSL certificates.
     * Use with caution - only for trusted development environments.
     */
    fun createTrustAllClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .sslSocketFactory(createTrustAllSSLSocketFactory(), createTrustAllTrustManager())
            .hostnameVerifier { _, _ -> true }
            .build()
    }
    
    private fun createTrustAllSSLSocketFactory(): SSLSocketFactory {
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, arrayOf(createTrustAllTrustManager()), null)
        return sslContext.socketFactory
    }
    
    private fun createTrustAllTrustManager(): X509TrustManager {
        return object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
                // Accept all client certificates
            }
            
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
                // Accept all server certificates
            }
            
            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        }
    }
}
