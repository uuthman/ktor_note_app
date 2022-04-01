package com.uuthman.routes

import com.uuthman.data.checkIfUserExists
import com.uuthman.data.collections.User
import com.uuthman.data.registerUser
import com.uuthman.data.requests.AccountRequest
import com.uuthman.data.responses.SimpleResponse
import io.ktor.application.*
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.registerRoute(){
    route("/register"){
        post {
            val request = try{
                call.receive<AccountRequest>()
            }catch (e: ContentTransformationException){
                call.respond(BadRequest)
                return@post
            }

            val userExists = checkIfUserExists(request.email)
            if(userExists){
                call.respond(OK,SimpleResponse(false,"User with email already exists"))
                return@post
            }

            if (!registerUser(User(request.email,request.password))){
                call.respond(OK,SimpleResponse(false,"An unknown error occured"))
                return@post
            }

            call.respond(OK,SimpleResponse(true,"Account created successfully"))
        }
    }
}