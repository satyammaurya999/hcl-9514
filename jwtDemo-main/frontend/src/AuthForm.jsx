import api from "./api";
import SubmitButton from "./SubmitButton";
import { useState, useActionState } from "react";

const AuthForm = ({ onLogin }) => {
  const [mode, setMode] = useState("login");
  const submitAction = async (prevState, formData) => {
    const username = formData.get("username")?.trim();
    const password = formData.get("password")?.trim();

    if (!username || !password) {
      return {
        error: "username and password is required",
        msg: "",
        username: username || "",
      };
    }
    try {
      if (mode == "register") {
        await api.post("/auth/register", { username, password });
        setMode("login");
        return {
          error: null,
          msg: "user registered successfully",
          username,
        };
      }
      // login mode
      const { data } = await api.post("/auth/login", { username, password });
      localStorage.setItem("token", data.token);
      localStorage.setItem("username", username);
      onLogin(username);
      return {
        error: null,
        msg: "",
        username: data.username,
      };
    } catch (error) {
      return {
        error:
          error.response?.data?.error ||
          error.response?.dta?.message ||
          "Something went wrong. Please try again.",
        msg: "",
        username,
      };
    }
  };
  const [state, formAction, isPending] = useActionState(submitAction, {
    error: null,
    msg: "",
    username: "",
  });
  return (
    <form
      action={formAction}
      className="max-w-md w-full mx-auto bg-white p-8 rounded-2xl border border-slate-200 shadow-sm space-y-5"
    >
      <h3 className="text-xl font-bold tracking-tight text-slate-900 text-center">
        {mode === "login" ? "Login" : "Register"}
      </h3>

      <div className="space-y-4">
        <div>
          <label
            htmlFor="username"
            className="block text-xs font-semibold uppercase tracking-wider text-slate-600 mb-1"
          >
            Username
          </label>
          <input
            type="text"
            name="username"
            id="username"
            placeholder="Enter your username"
            defaultValue={state.username}
            required
            disabled={isPending}
            className="w-full px-4 py-2.5 bg-slate-50 border border-slate-200 rounded-xl text-slate-900 placeholder:text-slate-400 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:bg-white disabled:opacity-50 disabled:cursor-not-allowed transition-all text-sm"
          />
        </div>

        <div>
          <label
            htmlFor="password"
            className="block text-xs font-semibold uppercase tracking-wider text-slate-600 mb-1"
          >
            Password
          </label>
          <input
            type="password"
            name="password"
            id="password"
            placeholder="Enter your password"
            required
            disabled={isPending}
            className="w-full px-4 py-2.5 bg-slate-50 border border-slate-200 rounded-xl text-slate-900 placeholder:text-slate-400 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:bg-white disabled:opacity-50 disabled:cursor-not-allowed transition-all text-sm"
          />
        </div>
      </div>

      <div className="space-y-3 pt-2">
        <SubmitButton />

        <button
          type="button"
          disabled={isPending}
          onClick={() => {
            setMode(mode === "login" ? "register" : "login");
          }}
          className="w-full py-2.5 px-4 text-sm font-medium text-slate-600 hover:text-slate-900 hover:bg-slate-100 rounded-xl transition-colors disabled:opacity-50 disabled:cursor-not-allowed cursor-pointer"
        >
          Switch to {mode === "login" ? "Register" : "Login"}
        </button>
      </div>

      {state.msg && (
        <p
          role="status"
          className="p-3 text-xs font-medium bg-emerald-50 text-emerald-700 border border-emerald-100 rounded-xl text-center"
        >
          {state.msg}
        </p>
      )}

      {state.error && (
        <p
          role="status"
          className="p-3 text-xs font-medium bg-red-50 text-red-700 border border-red-100 rounded-xl text-center"
        >
          {state.error}
        </p>
      )}
    </form>
  );
};
export default AuthForm;
