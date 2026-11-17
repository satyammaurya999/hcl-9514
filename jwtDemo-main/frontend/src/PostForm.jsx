import { useActionState } from "react";
import SubmitButton from "./SubmitButton";
import api from "./api";

const PostForm = ({ onCreated }) => {
  const submitAction = async (prev, formData) => {
    const title = formData.get("title")?.trim();
    const content = formData.get("content")?.trim();
    if (!title || !content) {
      return {
        error: "Title and content is required",
        msg: "",
        title: title || "",
        content: content || "",
      };
    }
    try {
      await api.post("/posts", { title, content });
      await onCreated?.();

      return {
        error: null,
        msg: "post created successfully",
        title: "",
        content: "",
      };
    } catch (error) {
      return {
        error:
          error.response?.data?.error ||
          error.response?.dta?.message ||
          "failed to create post. Please try again.",
        msg: "",
        title,
        content,
      };
    }
  };
  const [state, formAction, isPending] = useActionState(submitAction, {
    error: null,
    msg: "",
    title: "",
    content: "",
  });
  return (
    <form
      action={formAction}
      className="bg-white rounded-2xl border border-slate-200 p-6 shadow-sm space-y-5"
    >
      <h3 className="text-lg font-bold tracking-tight text-slate-900">
        Write a Post
      </h3>

      <div className="space-y-4">
        <div>
          <label
            htmlFor="title"
            className="block text-xs font-semibold uppercase tracking-wider text-slate-600 mb-1"
          >
            Title
          </label>
          <input
            type="text"
            name="title"
            id="title"
            placeholder="Enter post title"
            defaultValue={state.title}
            required
            disabled={isPending}
            className="w-full px-4 py-2.5 bg-slate-50 border border-slate-200 rounded-xl text-slate-900 placeholder:text-slate-400 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:bg-white disabled:opacity-50 disabled:cursor-not-allowed transition-all text-sm font-medium"
          />
        </div>

        <div>
          <label
            htmlFor="content"
            className="block text-xs font-semibold uppercase tracking-wider text-slate-600 mb-1"
          >
            Content
          </label>
          <textarea
            name="content"
            id="content"
            placeholder="What's on your mind?"
            rows={4}
            defaultValue={state.content}
            required
            disabled={isPending}
            className="w-full px-4 py-2.5 bg-slate-50 border border-slate-200 rounded-xl text-slate-900 placeholder:text-slate-400 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:bg-white disabled:opacity-50 disabled:cursor-not-allowed transition-all text-sm resize-y"
          ></textarea>
        </div>
      </div>

      <div className="pt-1">
        <SubmitButton />
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
export default PostForm;
