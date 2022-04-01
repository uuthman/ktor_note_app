package com.uuthman.routes

import com.uuthman.data.checkPasswordForEmail
import com.uuthman.data.requests.AccountRequest
import com.uuthman.data.responses.SimpleResponse
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.loginRoute(){
    route("/login"){
        post {
            val request = try{
                call.receive<AccountRequest>()
            }catch (e: ContentTransformationException){
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val isPasswordCorrect = checkPasswordForEmail(request.email,request.password)
            if(!isPasswordCorrect){
                call.respond(OK,SimpleResponse(false,"Incorrect credentials"))
                return@post
            }
            call.respond(OK,SimpleResponse(true,"Login successful"))
        }
    }
}