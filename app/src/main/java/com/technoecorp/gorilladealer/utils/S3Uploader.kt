package com.technoecorp.gorilladealer.utils

import android.content.Context
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.technoecorp.gorilladealer.BuildConfig
import com.technoecorp.gorilladealer.extensions.showShortExceptionToast
import timber.log.Timber
import java.io.File

class S3Uploader(private var applicationContext: Context) {
    private lateinit var s3: AmazonS3Client
    private lateinit var credentials: BasicAWSCredentials
    private lateinit var transferUtility: TransferUtility
    private val bucketName: String = "suraksha-images"
    private val pathUrl: String = "https://suraksha-images.s3.amazonaws.com/"

    fun init() {
        try {
            val secret: String =
                BuildConfig.AWS_SECRET
            val key: String = BuildConfig.AWS_KEY
            credentials = BasicAWSCredentials(key, secret)
            s3 = AmazonS3Client(credentials)
            s3.setRegion(Region.getRegion(Regions.AP_SOUTH_1))
            transferUtility = TransferUtility(s3, applicationContext)
        } catch (e: Exception) {
            applicationContext.showShortExceptionToast(e)
        }
    }

    fun uploadURL(tempUploadPath: String, fileName: String, callback: (String) -> Unit) {
        val file = File(tempUploadPath)
        val observer = transferUtility.upload(
            bucketName,
            fileName,
            file
        )
        observer.setTransferListener(object : TransferListener {
            override fun onStateChanged(id: Int, state: TransferState) {
                if (TransferState.COMPLETED == observer.state) {
                    val thread = Thread {
                        s3.setObjectAcl(
                            bucketName,
                            fileName,
                            CannedAccessControlList.PublicRead
                        )
                        val imagePath =
                            pathUrl + fileName
                        Timber.e("Image Path is $imagePath")
                        callback(imagePath)
                    }
                    thread.start()
                }
            }

            override fun onProgressChanged(id: Int, bytesCurrent: Long, bytesTotal: Long) {
                //Progress Update
            }

            override fun onError(id: Int, ex: java.lang.Exception) {
                applicationContext.showShortExceptionToast(ex)
            }
        })
    }


}